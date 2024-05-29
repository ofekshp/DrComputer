package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.viewmodel.UploadPostViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

// TODO: CHANGE POST ARGUMENT TO BINIDNG
 class PostUpload : Fragment() {
    private lateinit var postViewModel:UploadPostViewModel
    private lateinit var auth:FirebaseAuth
    private lateinit var post: PostEntity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       post = PostEntity("", "", "", "", "", "", "", "", "")
        parentFragmentManager.setFragmentResultListener("requestKey", this) { _, bundle ->
            post = bundle.getSerializable("key") as PostEntity}
            val view = inflater.inflate(R.layout.fragment_post_upload, container, false)
            val typeText: TextInputEditText = view.findViewById(R.id.typeUp)
            val cpuText: TextInputEditText = view.findViewById(R.id.cpuUp)
            val gpuText: TextInputEditText = view.findViewById(R.id.gpuUp)
            val motherboardText: TextInputEditText = view.findViewById(R.id.motherboardUp)
            val memoryText: TextInputEditText = view.findViewById(R.id.memoryUp)
            val ramText: TextInputEditText = view.findViewById(R.id.ramUp)

            auth = FirebaseAuth.getInstance()
            view.findViewById<Button>(R.id.btn_addImage)
                .setOnClickListener {
                    findNavController().navigate(
                        PostUploadDirections.actionPostUploadToPostImage(
                            post,
                            "PU"
                        )
                    )
                }

            view.findViewById<Button>(R.id.btn_upload)
                .setOnClickListener {
                    val type: String = typeText.text.toString()
                    val cpu: String = cpuText.text.toString()
                    val gpu: String = gpuText.text.toString()
                    val motherboard: String = motherboardText.text.toString()
                    val memory: String = memoryText.text.toString()
                    val ram: String = ramText.text.toString()
                    if (validate(type, cpu, gpu, motherboard, memory, ram)) {
                        if (auth.currentUser != null) {
                            postViewModel = ViewModelProvider(this)[UploadPostViewModel::class.java]
                            val newPost: PostEntity = PostEntity(
                                "",
                                type,
                                cpu,
                                gpu,
                                motherboard,
                                memory,
                                ram,
                                auth.currentUser!!.uid,
                                post.postImage
                            )
                            postViewModel.uploadPost(newPost) { isSuccessful ->
                                if (isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Uploaded successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().popBackStack()
                                } else
                                    Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT)
                                        .show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill in all the information correctly",
                            Toast.LENGTH_SHORT
                        ).show()
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