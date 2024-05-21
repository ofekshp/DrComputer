package com.example.drcomputer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.adapter.PostAdapter
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.viewmodel.GetMyPostsViewModel
import com.example.drcomputer.viewmodel.GetPostsViewModel
import com.google.firebase.auth.FirebaseAuth


class MyPosts : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var postViewModel: GetMyPostsViewModel
    private lateinit var newRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_posts, container, false)
        postViewModel= ViewModelProvider(this)[GetMyPostsViewModel::class.java]
        newRecyclerView = view.findViewById(R.id.my_posts)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        auth=FirebaseAuth.getInstance()
        var myList=arrayListOf<PostEntity>()
        if(auth.currentUser!=null)
            myList=getMyPosts(auth.currentUser!!.uid)
        for( post in myList)
        {
            println(post.cpu)
        }
        newRecyclerView.adapter= PostAdapter(myList)
        return view
    }


    private fun getMyPosts(uid: String):ArrayList<PostEntity>  {


        val list:ArrayList<PostEntity> = arrayListOf<PostEntity>()
        postViewModel.getMyPosts(uid)
        postViewModel.posts.observe(viewLifecycleOwner){posts->
            for (post in posts)
            {
                list.add(post)
                println( list.get(list.size-1).cpu)
            }
        }
        return list
    }

}
