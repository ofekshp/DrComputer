package com.example.drcomputer.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.viewmodel.EditPostViewModel
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class PostImage : Fragment() {
    private lateinit var editPostViewModel: EditPostViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private lateinit var btnSave: Button
    private lateinit var imageViewPost: ImageView
    private var imageUrlRef:String = ""
    private var imageUri: Uri? = null
    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            //upload the image to Firebase Storage
            uploadImage()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_image, container, false)
        editPostViewModel = ViewModelProvider(this)[EditPostViewModel::class.java]
        val postEntity: PostEntity = arguments?.getSerializable("post") as PostEntity
        val changeImageBtn: Button = view.findViewById(R.id.edit_post_img)
        val deleteImageBtn: Button = view.findViewById(R.id.delete_post_img)
        btnSave = view.findViewById(R.id.btn_save)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar2 = view.findViewById(R.id.progressBar2)
        imageViewPost = view.findViewById(R.id.edit_post_image)

        if (!postEntity.postImage.isNullOrEmpty()) {
            Picasso.get().load(postEntity.postImage).into(imageViewPost)
            imageUrlRef = postEntity.postImage
        }

        changeImageBtn.setOnClickListener{
            imagePicker.launch("image/*")
        }

        deleteImageBtn.setOnClickListener{
            Picasso.get().load(R.drawable.question_mark).into(imageViewPost)
            imageUrlRef = ""
        }

        btnSave.setOnClickListener{
            postEntity.postImage = imageUrlRef
            findNavController().popBackStack()
        }

        return view
    }

    private fun uploadImage() {
        imageUri?.let {
            val storageReference = FirebaseStorage.getInstance().reference.child("post_images/${System.currentTimeMillis()}.jpg")
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
                            Picasso.get().load(imageUrl).into(imageViewPost)
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