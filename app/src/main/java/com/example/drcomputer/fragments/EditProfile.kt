package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.UserEntity
import com.example.drcomputer.viewmodel.EditProfileViewModel
import com.example.drcomputer.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

class EditProfile : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var editProfileViewModel: EditProfileViewModel

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
        val btnSave: Button = view.findViewById(R.id.btn_save)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        var email: String
        var password: String
        var userName: String

        profileViewModel.getUserByUid(uid){ userEntity ->
            if(userEntity!=null){
                userNameText.text= userEntity.userName
                emailText.text= userEntity.email
            }
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
                val user = UserEntity(uid, userName, email)
                editProfileViewModel.editProfile(user, password) { success ->
                    if (success)
                        Toast.makeText(context, "New Profile Save", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }
}