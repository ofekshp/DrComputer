package com.example.drcomputer

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val emailText: TextInputEditText = view.findViewById(R.id.emailReg)
        val passwordText: TextInputEditText = view.findViewById(R.id.passwordReg)
        val btnReg: Button = view.findViewById(R.id.btn_register)
        var auth: FirebaseAuth = FirebaseAuth.getInstance()


        btnReg.setOnClickListener {
            val email: String
            val password: String
            email = emailText.text.toString()
            password = passwordText.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(context, "Enter Email", Toast.LENGTH_SHORT).show()
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show()
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                       Toast.makeText(context,"Register Success",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context,"Register Failed",Toast.LENGTH_SHORT).show()
                    }
                }

        }


        return view;
    }

}