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
import com.example.drcomputer.adapter.MyPostsAdapter
import com.example.drcomputer.viewmodel.GetPostsViewModel

class homePage : Fragment() {

    private lateinit var postViewModel: GetPostsViewModel
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var myAdapter: MyPostsAdapter
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
        val buttonArticles=view.findViewById<Button>(R.id.btn_article)
        buttonArticles.setOnClickListener{
            findNavController().navigate(R.id.action_homePage_to_articles)
        }
        postViewModel= ViewModelProvider(this)[GetPostsViewModel::class.java]
        newRecyclerView = view.findViewById(R.id.post_view)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        myAdapter= MyPostsAdapter(findNavController(),"HOME",context)
        newRecyclerView.adapter= myAdapter
        fetchUserPosts()
        observeUserPosts()
        return view
    }
    private fun observeUserPosts() {
        postViewModel.posts.observe(viewLifecycleOwner) { posts ->
            val postsArrayList = ArrayList(posts)
            myAdapter.submitList(postsArrayList)
        }
    }
    private fun fetchUserPosts() {
        // Call the getUserPosts function to start observing the LiveData
        postViewModel.getAllPosts()
    }

}