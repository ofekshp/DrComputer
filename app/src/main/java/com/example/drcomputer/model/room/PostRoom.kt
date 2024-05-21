package com.example.drcomputer.model.room

import com.example.drcomputer.model.entities.PostEntity

class PostRoom {
    fun getAllPosts(): List<PostEntity> {
        return AppLocalDB.getInstance().postDao().getAll()
    }

    fun insertPost(post: PostEntity) {
        val db = AppLocalDB.getInstance().postDao().insert(post)
    }

    fun editPost(post: PostEntity){
        return AppLocalDB.getInstance().postDao().editPost(post)
    }

    fun deletePost(post: PostEntity) {
        return AppLocalDB.getInstance().postDao().delete(post)
    }

    fun getPostsByUid(uid: String): List<PostEntity> {
        return AppLocalDB.getInstance().postDao().getPostByUserId(uid)
    }
}