package com.example.drcomputer.model.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.drcomputer.model.entities.PostEntity

@Dao
interface PostDao
{
    @Query("SELECT * FROM posts")
    fun getAll():List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post:PostEntity)

    @Update
    fun editPost(post: PostEntity)

    @Delete
    fun delete(post: PostEntity)

    @Query("SELECT * FROM posts WHERE uid =:uid")
    fun getPostByUserId(uid: String): List<PostEntity>
}