package com.example.drcomputer.fragments

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
import androidx.navigation.fragment.findNavController
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.viewmodel.DeletePostViewModel
import com.example.drcomputer.viewmodel.EditPostViewModel

class EditPost : Fragment() {
    private lateinit var editPostViewModel: EditPostViewModel
    private lateinit var deletePostViewMode: DeletePostViewModel
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        editPostViewModel = ViewModelProvider(this)[EditPostViewModel::class.java]
        deletePostViewMode=ViewModelProvider(this)[DeletePostViewModel::class.java]
        progressBar = view.findViewById(R.id.progressBar)
        val post:PostEntity = arguments?.getSerializable("post") as PostEntity
        val typeText: TextView = view.findViewById(R.id.typeUp)
        val cpuText: TextView = view.findViewById(R.id.cpuUp)
        val gpuText: TextView = view.findViewById(R.id.gpuUp)
        val motherboardText: TextView = view.findViewById(R.id.motherboardUp)
        val memoryText: TextView = view.findViewById(R.id.memoryUp)
        val ramText: TextView = view.findViewById(R.id.ramUp)

        typeText.text = post.type
        cpuText.text = post.cpu
        gpuText.text = post.gpu
        motherboardText.text = post.motherboard
        memoryText.text = post.memory
        ramText.text = post.ram

        view.findViewById<Button>(R.id.btn_addImage)
            .setOnClickListener{
                findNavController().navigate(EditPostDirections.actionEditPostToPostImage(post))
            }

        view.findViewById<Button>(R.id.btn_deletePost)
            .setOnClickListener{
                deletePostViewMode.deletePost(post){success->
                    if(success){
                        Toast.makeText(context, "Deleted successful", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    else{
                        Toast.makeText(context, "Sorry couldn't delete post", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        view.findViewById<Button>(R.id.btn_saveEditPost)
            .setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val type: String = typeText.text.toString()
                val cpu: String = cpuText.text.toString()
                val gpu: String = gpuText.text.toString()
                val motherboard: String = motherboardText.text.toString()
                val memory: String = memoryText.text.toString()
                val ram: String = ramText.text.toString()

                post.type=type
                post.cpu=cpu
                post.gpu=gpu
                post.motherboard=motherboard
                post.memory=memory
                post.ram=ram

                if(validate(type,cpu,gpu,motherboard,memory,ram))
                {
                    editPostViewModel.editPost(post){success ->
                        if (success) {
                            Toast.makeText(context, "New Post Save", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                            findNavController().popBackStack()
                        }
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