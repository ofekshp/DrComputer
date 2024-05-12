package com.example.drcomputer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class homePage : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        val buttonProfile = view.findViewById<Button>(R.id.btn_progile)
        buttonProfile.setOnClickListener{
            findNavController().navigate(R.id.action_homePage_to_myProfileFragment)
        }
        return view
    }

}