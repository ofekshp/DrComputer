package com.example.drcomputer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity

class GetPostsViewModel:ViewModel() {
    private lateinit var _posts: LiveData<List<PostEntity>>
    private lateinit var _userPosts: MutableLiveData<List<PostEntity>>;
    val postsModel = CompletePostModel()
    val posts: LiveData<List<PostEntity>> get() = _posts
    val userPosts: LiveData<List<PostEntity>> get() = _userPosts
    fun getAllPosts(){
        _posts = postsModel.getAllPosts()
    }

    fun getMyPosts(uid: String) {
        this._userPosts = postsModel.getPostsByUid(uid)
    }

}