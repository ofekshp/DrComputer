package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.CompletePostModel
import com.example.drcomputer.model.entities.PostEntity


class DeletePostViewModel: ViewModel() {
    val postModel=CompletePostModel()
    fun deletePost(post: PostEntity, callback: (Boolean) -> Unit) {
        return postModel.deletePost(post, callback)
    }
}
