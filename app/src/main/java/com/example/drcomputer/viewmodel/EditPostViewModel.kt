package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity

class EditPostViewModel: ViewModel() {
    val postModel = CompletePostModel()

    fun editPost(post : PostEntity, callback: (Boolean) -> Unit){
        postModel.editPost(post, callback)
    }
}