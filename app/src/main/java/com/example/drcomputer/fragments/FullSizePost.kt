package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso

class FullSizePost : Fragment() {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var spinner: ProgressBar
    private lateinit var progressBar: ProgressBar
    private lateinit var contentLayout: ConstraintLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {


        val view = inflater.inflate(R.layout.fragment_full_size_post, container, false)
        spinner = view.findViewById(R.id.spinner)
        progressBar = view.findViewById(R.id.progressBar)
        contentLayout = view.findViewById(R.id.contentLayout)
        val post: PostEntity = arguments?.getSerializable("post") as PostEntity
        var type= view.findViewById<TextView>(R.id.type)
        var cpu=view.findViewById<TextView>(R.id.cpu)
        var gpu= view.findViewById<TextView>(R.id.gpu)
        var motherboard= view.findViewById<TextView>(R.id.motherboard)
        var memory= view.findViewById<TextView>(R.id.memory)
        var ram= view.findViewById<TextView>(R.id.ram)
        var userName=view.findViewById<TextView>(R.id.userName)
        var imgPost= view.findViewById<ImageView>(R.id.imgPost)
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        profileViewModel.getUserByUid(post.uid){ userEntity ->
            if(userEntity!=null){
                userName.text= userEntity.userName
            }
            else{
                userName.text="Error404"
            }
            spinner.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
        }
        type.text="type: "+post.type
        cpu.text="cpu: "+post.cpu
        gpu.text="gpu: "+post.gpu
        motherboard.text="motherboard: "+post.motherboard
        memory.text="memory: "+post.memory
        ram.text="ram: "+post.ram
        if (!post.postImage.isNullOrEmpty()) {
            progressBar.visibility = View.VISIBLE
            Picasso.get().load(post.postImage).into(imgPost)
            progressBar.visibility = View.GONE
        }
        else
            Picasso.get().load(R.drawable.question_mark).into(imgPost)
        return view
    }
}

