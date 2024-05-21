package com.example.drcomputer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity
import com.google.firebase.auth.FirebaseAuth

class GetMyPostsViewModel:ViewModel() {
    private val postsModel = CompletePostModel()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // MutableLiveData to hold posts by UID data
    private lateinit var _userPosts: MutableLiveData<List<PostEntity>>;
    val userPosts: LiveData<List<PostEntity>> get() = _userPosts
    private val user= auth.currentUser
    val uid= user!!.uid
    fun getMyPosts(uid: String) {
        this._userPosts = postsModel.getPostsByUid(uid)
    }
}