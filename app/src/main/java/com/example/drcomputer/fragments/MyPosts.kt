package com.example.drcomputer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.adapter.MyPostsAdapter
import com.example.drcomputer.viewmodel.GetPostsViewModel
import com.google.firebase.auth.FirebaseAuth


class MyPosts : Fragment() {
    private var auth:FirebaseAuth=FirebaseAuth.getInstance()
    private lateinit var postViewModel: GetPostsViewModel
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var myAdapter: MyPostsAdapter
    private val user= auth.currentUser
    val uid= user?.uid
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_posts, container, false)
        postViewModel= ViewModelProvider(this)[GetPostsViewModel::class.java]
        newRecyclerView = view.findViewById(R.id.my_posts)
        myAdapter= MyPostsAdapter()

        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        if(user!=null) {
            newRecyclerView.adapter= myAdapter
        }
        fetchUserPosts()
        observeUserPosts()

        return view
    }
    private fun observeUserPosts() {
        postViewModel.userPosts.observe(viewLifecycleOwner) { posts ->
            val postsArrayList = ArrayList(posts)
            myAdapter.submitList(postsArrayList)
        }
    }
    private fun fetchUserPosts() {
        // Call the getUserPosts function to start observing the LiveData
        postViewModel.getMyPosts(uid.toString())
    }
}
