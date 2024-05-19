package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.adapter.PostAdapter
import com.example.drcomputer.model.Post

class homePage : Fragment() {
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
        newRecyclerView = view.findViewById(R.id.post_view)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        newList=getData()
        newRecyclerView.adapter= PostAdapter(newList)
        return view
    }


    private fun getData():ArrayList<Post>  {
        val list:ArrayList<Post> = arrayListOf<Post>()
        for (number in 1..10) {
            //TODO get data from room
            val post = Post("Title Number $number", "This is subtitle number $number")
            list.add(post)
        }
        return list
    }

}