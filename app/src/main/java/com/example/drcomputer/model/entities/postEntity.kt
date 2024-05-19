package com.example.drcomputer.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    var pid: Int,
    var type: String,
    var cpu: String,
    var gpu: String,
    var motherboard: String,
    var memory: String,
    var ram: String
): Serializable
