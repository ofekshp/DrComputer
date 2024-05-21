package com.example.drcomputer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity

class GetPostsViewModel:ViewModel() {
    private lateinit var _posts: LiveData<List<PostEntity>>
    val postsModel = CompletePostModel()
    val posts: LiveData<List<PostEntity>> get() = _posts
    fun getAllPosts(){
        _posts = postsModel.getAllPosts()
    }

}