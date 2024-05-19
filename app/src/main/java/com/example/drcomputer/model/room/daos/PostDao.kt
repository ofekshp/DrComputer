package com.example.drcomputer.model.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.drcomputer.model.entities.PostEntity

@Dao
interface PostDao
{
    @Query("SELECT * FROM posts")
    fun getAll():ArrayList<PostEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post:PostEntity)


    @Delete
    fun delete(post: PostEntity)

    @Query("SELECT * FROM posts WHERE pid =:pid")
    fun getPostById(pid: String): ArrayList<PostEntity>?
}