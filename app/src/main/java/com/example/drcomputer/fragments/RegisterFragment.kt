package com.example.drcomputer.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drcomputer.R
import com.example.drcomputer.activities.MainActivity
import com.example.drcomputer.model.entities.UserEntity
import com.example.drcomputer.viewmodel.RegisterUserViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterFragment : Fragment() {
    private lateinit var registerUserViewModel: RegisterUserViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val emailText: TextInputEditText = view.findViewById(R.id.emailReg)
        val passwordText: TextInputEditText = view.findViewById(R.id.passwordReg)
        val userNameText: TextInputEditText = view.findViewById(R.id.userNameReg)
        val btnReg: Button = view.findViewById(R.id.btn_register)
        registerUserViewModel = ViewModelProvider(this)[RegisterUserViewModel::class.java]
        progressBar = view.findViewById(R.id.progressBar)

        btnReg.setOnClickListener {
            val email: String
            val password: String
            val userName: String
            email = emailText.text.toString()
            password = passwordText.text.toString()
            userName = userNameText.text.toString()
            if (email.isNullOrEmpty() || password.isNullOrEmpty() || userName.isNullOrEmpty()) {
                Toast.makeText(context, "Something is missing", Toast.LENGTH_LONG).show()
            }
            else {
                val user = UserEntity("",userName,email,"")
                progressBar.visibility = View.VISIBLE
                btnReg.isClickable = false
                registerUserViewModel.register(user,password){isSuccessful ->
                    if(isSuccessful)
                    {
                        progressBar.visibility = View.GONE
                        val msg:String = "Register Success"
                        val toast = Toast.makeText(context,msg , Toast.LENGTH_SHORT)
                        val view = toast.view
                        val text: TextView? = view?.findViewById(android.R.id.message)
                        text?.setTextColor(Color.RED)
                        toast.show()
                        btnReg.isClickable = true
                        val submitActivityIntent = Intent(context, MainActivity::class.java)
                        startActivity(submitActivityIntent)
                    }
                    else {
                        Toast.makeText(context, "Register Failed", Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                        btnReg.isClickable = true
                    }
                }
            }
        }
        return view;
    }

}