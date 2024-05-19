package com.example.drcomputer.model.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.drcomputer.model.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity);

    @Transaction
    @Query("SELECT * FROM users where uid = :uid")
    fun getUserById(uid: String): UserEntity;

    @Update
    fun updateUser(user: UserEntity)
}