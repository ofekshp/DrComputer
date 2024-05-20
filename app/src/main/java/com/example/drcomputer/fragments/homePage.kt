package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.adapter.PostAdapter
import com.example.drcomputer.model.Post
import com.example.drcomputer.viewmodel.GetPostsViewModel
import com.example.drcomputer.viewmodel.UploadPostViewModel

class homePage : Fragment() {
    private lateinit var postViewModel: GetPostsViewModel
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newList: ArrayList<Post>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)
        val buttonProfile = view.findViewById<Button>(R.id.btn_profile)
        buttonProfile.setOnClickListener{
            findNavController().navigate(R.id.action_homePage_to_myProfileFragment)
        }
        val buttonUpload=view.findViewById<Button>(R.id.btn_upload)
        buttonUpload.setOnClickListener{
            findNavController().navigate(R.id.action_homePage_to_postUpload)
        }
        postViewModel= ViewModelProvider(this)[GetPostsViewModel::class.java]
        newRecyclerView = view.findViewById(R.id.post_view)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        newList=getPosts()
        newRecyclerView.adapter= PostAdapter(newList)
        return view
    }


    private fun getPosts():ArrayList<Post>  {


        val list:ArrayList<Post> = arrayListOf<Post>()
        postViewModel.getAllPosts()
        postViewModel.posts.observe(viewLifecycleOwner){posts->
           for (post in posts) {
               val item = Post("Computer type:  ${post.type}", "Cpu:  ${post.cpu}")
               list.add(item)
           }
        }
        return list
    }

}