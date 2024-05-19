package com.example.drcomputer.model.firebase

import com.example.drcomputer.model.Post
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.model.entities.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostFB {
    private lateinit var auth: FirebaseAuth



    fun postCollection(
         pid: String,
         type: String,
         cpu: String,
         gpu: String,
         motherboard: String,
         memory: String,
         ram: String,
        callback: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        val data = hashMapOf(
            "pid" to pid,
            "type" to type,
            "cpu" to cpu,
            "gpu" to gpu,
            "motherboard" to motherboard,
            "memory" to memory,
            "ram" to ram,
        )
        db.collection("posts").document().set(data)
            .addOnSuccessListener {
                println("User uploaded successfully with PID: $pid")
                callback(true)
            }
            .addOnFailureListener { exception ->
                println("Error uploading user: ${exception.message}")
                callback(false)
            }
    }




    fun getPostByPid(pid: String, callback: (PostEntity?) -> Unit) {
        val db = Firebase.firestore
        val usersCollection = db.collection("posts")

        usersCollection.whereEqualTo("pid", pid)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val docResult = result.documents.first()
                    val type = docResult.getString("type") ?: ""
                    val cpu = docResult.getString("cpu") ?: ""
                    val gpu = docResult.getString("gpu") ?: ""
                    val motherboard = docResult.getString("motherboard") ?: ""
                    val memory = docResult.getString("memory") ?: ""
                    val ram = docResult.getString("ram") ?: ""

                    val postEntity = PostEntity(
                        pid= pid,
                        type=type,
                        cpu=cpu,
                        gpu=gpu,
                        motherboard=motherboard,
                        memory=memory,
                        ram=ram,
                    )
                    callback(postEntity)
                } else {
                    println("No document found with UID $pid")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching user document: ${exception.message}")
                callback(null)
            }
    }
}