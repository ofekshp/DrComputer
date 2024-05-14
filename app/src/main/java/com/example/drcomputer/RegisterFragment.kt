package com.example.drcomputer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val emailText: TextInputEditText = view.findViewById(R.id.emailReg)
        val passwordText: TextInputEditText = view.findViewById(R.id.passwordReg)
        val userNameText: TextInputEditText = view.findViewById(R.id.userNameReg)
        val btnReg: Button = view.findViewById(R.id.btn_register)
        auth = FirebaseAuth.getInstance()

        btnReg.setOnClickListener {
            val email: String
            val password: String
            val userName: String
            email = emailText.text.toString()
            password = passwordText.text.toString()
            userName = userNameText.text.toString()
            if (email.isNullOrEmpty() || password.isNullOrEmpty() || userName.isNullOrEmpty()) {
                Toast.makeText(context, "Something is missing", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                            val submitActivityIntent = Intent(context, MainActivity::class.java)
                            startActivity(submitActivityIntent)
                        } else {
                            Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


        return view;
    }

}