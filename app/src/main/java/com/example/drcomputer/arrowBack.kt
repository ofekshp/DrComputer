package com.example.drcomputer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController

class arrowBack : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_arrowback, container, false)
        val backButton=view.findViewById<ImageButton>(R.id.btn_arrowBack)
        backButton.setOnClickListener{
            findNavController().popBackStack()
        }

        return view
    }
}