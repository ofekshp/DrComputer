package com.example.drcomputer.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    var uid: String,

    var userName: String,

    var email: String
): Serializable

