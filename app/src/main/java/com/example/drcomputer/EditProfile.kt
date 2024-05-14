package com.example.drcomputer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

class EditProfile : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val emailText: TextInputEditText = view.findViewById(R.id.emailEd)
        val passwordText: TextInputEditText = view.findViewById(R.id.passwordEd)
        val userNameText: TextInputEditText = view.findViewById(R.id.userNameEd)
        val btnSave: Button = view.findViewById(R.id.btn_save)
        auth=FirebaseAuth.getInstance()
        val user: FirebaseUser? =auth.currentUser
        if(user!=null) {
            btnSave.setOnClickListener {

                if (!passwordText.text.isNullOrEmpty())
                    updatePassword(user, passwordText.text.toString())
                if(!userNameText.text.isNullOrEmpty())
                    updateDisplayName(user,userNameText.text.toString())
                if(!emailText.text.isNullOrEmpty())
                    updateEmail(user,emailText.text.toString())
            }
        }

        return view
    }

    fun updatePassword(user:FirebaseUser,newPassword: String) {
        user.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Password updated successfully
                    println("Password updated successfully")
                } else {
                    // Handle error
                    println("Failed to update password: ${task.exception?.message}")
                }
            }
    }
    fun updateEmail(user: FirebaseUser, newEmail: String) {
        user.verifyBeforeUpdateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Email updated successfully
                    println("Email updated successfully")
                } else {
                    // Handle error
                    println("Failed to update email: ${task.exception?.message}")
                }
            }
    }
    fun updateDisplayName(user: FirebaseUser, newDisplayName: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newDisplayName)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Display name updated successfully
                    println("Display name updated successfully")
                } else {
                    // Handle error
                    println("Failed to update display name: ${task.exception?.message}")
                }
            }
    }
}