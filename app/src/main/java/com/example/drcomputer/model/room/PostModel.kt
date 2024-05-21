package com.example.drcomputer.model.room

import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.model.room.AppLocalDB

class PostModel {
    fun getAllPosts(): List<PostEntity> {
        return AppLocalDB.getInstance().postDao().getAll()
    }

    fun insertPost(post: PostEntity) {
        val db = AppLocalDB.getInstance().postDao().insert(post)
    }

    fun deletePost(post: PostEntity) {
        return AppLocalDB.getInstance().postDao().delete(post)
    }

    fun getPostsByUid(uid: String): List<PostEntity> {
        return AppLocalDB.getInstance().postDao().getPostByUserId(uid)
    }
}