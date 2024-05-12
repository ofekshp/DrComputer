package com.example.drcomputer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class mainPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mainpage, container, false)
        val buttonLogin = view.findViewById<Button>(R.id.btn_goToLogin)
        buttonLogin.setOnClickListener{
            findNavController().navigate(R.id.action_mainPageFragment_to_loginFragment)
        }
        val buttonRegister = view.findViewById<Button>(R.id.btn_goToRegister)
        buttonRegister.setOnClickListener{
            findNavController().navigate(R.id.action_mainPageFragment_to_registerFragment)
        }

        return view
    }
}