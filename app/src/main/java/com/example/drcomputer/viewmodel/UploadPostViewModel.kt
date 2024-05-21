package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity

class UploadPostViewModel: ViewModel() {
    val PostsModel = CompletePostModel()
    fun uploadPost(post : PostEntity, callback: (Boolean) -> Unit){
        PostsModel.uploadPost(post, callback)
    }
}