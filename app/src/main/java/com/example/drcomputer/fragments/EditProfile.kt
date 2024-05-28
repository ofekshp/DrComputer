package com.example.drcomputer.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.UserEntity
import com.example.drcomputer.viewmodel.EditProfileViewModel
import com.example.drcomputer.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class EditProfile : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var btnSave: Button
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var editProfileViewModel: EditProfileViewModel
    private lateinit var imageViewProfile: ImageView
    private var imageUrlRef:String = ""
    private var imageUri: Uri? = null
    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            //upload the image to Firebase Storage
            uploadImage()
        }
    }
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBar2: ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        editProfileViewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        val emailText: TextView = view.findViewById(R.id.emailEd)
        val passwordText: TextView = view.findViewById(R.id.passwordEd)
        val userNameText: TextView = view.findViewById(R.id.userNameEd)
        btnSave = view.findViewById(R.id.btn_save)
        val changeImageBtn : Button = view.findViewById(R.id.edit_profile_img)
        val deleteImageBtn : Button = view.findViewById(R.id.delete_profile_img)
        imageViewProfile = view.findViewById(R.id.edit_user_image)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar2 = view.findViewById(R.id.progressBar2)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        var email: String
        var password: String
        var userName: String

        progressBar.visibility = View.VISIBLE
        profileViewModel.getUserByUid(uid){ userEntity ->
            if(userEntity!=null){
                userNameText.text= userEntity.userName
                emailText.text= userEntity.email
                if (userEntity.profileImg.isNotEmpty()) {
                    Picasso.get().load(userEntity.profileImg).into(imageViewProfile)
                    imageUrlRef = userEntity.profileImg
                }
                progressBar.visibility = View.GONE
            }
        }

        changeImageBtn.setOnClickListener{
            imagePicker.launch("image/*")
        }

        deleteImageBtn.setOnClickListener{
            Picasso.get().load(R.drawable.default_img_profile).into(imageViewProfile)
            imageUrlRef = ""
        }

        btnSave.setOnClickListener {
            email = emailText.text.toString()
            password = passwordText.text.toString()
            userName = userNameText.text.toString()
            if(!isValidEmail(email)) {
                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                val user = UserEntity(uid, userName, email,imageUrlRef)
                progressBar2.visibility = View.VISIBLE
                editProfileViewModel.editProfile(user, password) { success ->
                    if (success) {
                        Toast.makeText(context, "New Profile Save", Toast.LENGTH_SHORT).show()
                        progressBar2.visibility = View.GONE
                        findNavController().popBackStack()
                    }
                    else
                        Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }

    private fun uploadImage() {
        imageUri?.let {
            val storageReference = FirebaseStorage.getInstance().reference.child("profile_images/${System.currentTimeMillis()}.jpg")
            progressBar.visibility = View.VISIBLE
            btnSave.isClickable = false
            storageReference.putFile(it)
                .addOnSuccessListener {
                    // Image uploaded successfully
                    Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show()

                    // Get the download URL of the uploaded image
                    storageReference.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                            val imageUrl = downloadUri.toString()
                            imageUrlRef = imageUrl
                            Picasso.get().load(imageUrl).into(imageViewProfile)
                            progressBar.visibility = View.GONE
                            btnSave.isClickable = true
                        }
                        .addOnFailureListener { e ->
                            // Handle download URL retrieval failure
                            Toast.makeText(context, "Failed to get download URL: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    // Handle failed upload
                    Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
