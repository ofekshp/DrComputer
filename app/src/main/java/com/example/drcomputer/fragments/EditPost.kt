package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.viewmodel.EditPostViewModel
import com.google.android.material.textfield.TextInputEditText

class EditPost : Fragment() {
    private lateinit var editPostViewModel: EditPostViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        editPostViewModel = ViewModelProvider(this)[EditPostViewModel::class.java]

        val typeText: TextInputEditText = view.findViewById(R.id.typeUp)
        val cpuText: TextInputEditText = view.findViewById(R.id.cpuUp)
        val gpuText: TextInputEditText = view.findViewById(R.id.gpuUp)
        val motherboardText: TextInputEditText = view.findViewById(R.id.motherboardUp)
        val memoryText: TextInputEditText = view.findViewById(R.id.memoryUp)
        val ramText: TextInputEditText = view.findViewById(R.id.ramUp)

        view.findViewById<Button>(R.id.btn_saveEditPost)
            .setOnClickListener {
                val type: String = typeText.text.toString()
                val cpu: String = cpuText.text.toString()
                val gpu: String = gpuText.text.toString()
                val motherboard: String = motherboardText.text.toString()
                val memory: String = memoryText.text.toString()
                val ram: String = ramText.text.toString()

                val post = arguments?.getSerializable("post") as PostEntity
                if(validate(type,cpu,gpu,motherboard,memory,ram))
                {
                   val newPost = PostEntity(post.pid,type,cpu,gpu,motherboard,memory,ram,post.uid)
                    editPostViewModel.editPost(newPost){success ->
                        if (success)
                            Toast.makeText(context, "New Post Save", Toast.LENGTH_SHORT).show()
                        else
                            Toast.makeText(context, "something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        return view
    }

    private fun validate(
        type: String, cpu: String, gpu: String, motherboard: String, memory: String, ram: String

    ): Boolean {
        val isTypeValid = type.isNotEmpty()
        val isCpuValid = cpu.isNotEmpty()
        val isGpuValid = gpu.isNotEmpty()
        val isMotherboardValid = motherboard.isNotEmpty()
        val isMemoryValid = memory.isNotEmpty()
        val isRamValid = ram.isNotEmpty()

        return isTypeValid && isCpuValid && isGpuValid  && isMotherboardValid && isMemoryValid && isRamValid
    }

}