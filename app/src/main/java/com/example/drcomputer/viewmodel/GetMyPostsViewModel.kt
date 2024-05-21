package com.example.drcomputer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity

class GetMyPostsViewModel:ViewModel() {
    private lateinit var _myPosts: LiveData<List<PostEntity>>
    val postsModel = CompletePostModel()
    val posts: LiveData<List<PostEntity>> get() = _myPosts
    fun getMyPosts(uid:String){
        _myPosts=postsModel.getPostsByUid(uid)
    }
}