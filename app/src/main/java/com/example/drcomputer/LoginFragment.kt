package com.example.drcomputer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val emailText: TextInputEditText = view.findViewById(R.id.emailLog)
        val passwordText: TextInputEditText = view.findViewById(R.id.passwordLog)
        val btnLog: Button = view.findViewById(R.id.btn_login)
        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        btnLog.setOnClickListener{
            val email: String
            val password: String
            email = emailText.text.toString()
            password = passwordText.text.toString()

            if (email.isNullOrEmpty() && password.isNullOrEmpty()) {
                Toast.makeText(context, "Enter Email & Password", Toast.LENGTH_SHORT).show()
            }
            else if (password.isNullOrEmpty()) {
                Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show()
            }
            else if (email.isNullOrEmpty()) {
                Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return view
    }

}