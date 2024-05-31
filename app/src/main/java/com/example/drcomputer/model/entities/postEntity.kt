package com.example.drcomputer.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class PostEntity (

    @PrimaryKey()
    var pid:String,
    var type: String,
    var cpu: String,
    var gpu: String,
    var motherboard: String,
    var memory: String,
    var ram: String,
    var uid:String,
    var userName:String,
    @ColumnInfo(name = "post_img")
    var postImage: String
): Serializable{
    fun fromMap(map: Map<String, Any>)
    {
        type = map["type"].toString()
        cpu = map["cpu"].toString()
        gpu = map["gpu"].toString()
        motherboard = map["motherboard"].toString()
        memory = map["memory"].toString()
        ram = map["ram"].toString()
        uid = map["uid"].toString()
        userName = map["userName"].toString()
        postImage = map["postImage"].toString()
    }

}
