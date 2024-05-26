package com.example.drcomputer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.drcomputer.R
import com.example.drcomputer.activities.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val emailText: TextInputEditText = view.findViewById(R.id.emailLog)
        val passwordText: TextInputEditText = view.findViewById(R.id.passwordLog)
        val btnLog: Button = view.findViewById(R.id.btn_login)
        progressBar = view.findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()
        btnLog.setOnClickListener{
            val email: String
            val password: String
            email = emailText.text.toString()
            password = passwordText.text.toString()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(context, "Something is missing", Toast.LENGTH_SHORT).show()
            }
            else {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                            val submitActivityIntent = Intent(context, MainActivity::class.java)
                            startActivity(submitActivityIntent)
                        } else {
                            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return view
    }

}