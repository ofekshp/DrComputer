package com.example.drcomputer.model.firebase

import com.example.drcomputer.model.entities.PostEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.LinkedList

class PostFB {
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    fun uploadPost(post:PostEntity, callback: (Boolean) -> Unit) {

        val docRef = db.collection("posts").document()
        val gen=docRef.id
        val data = hashMapOf(
            "pid" to gen,
            "type" to post.type,
            "cpu" to post.cpu,
            "gpu" to post.gpu,
            "motherboard" to post.motherboard,
            "memory" to post.memory,
            "ram" to post.ram,
            "uid" to post.uid,
            "postImage" to post.postImage
        )
        docRef.set(data)
            .addOnSuccessListener {
                println("User uploaded successfully with PID: $post.pid")
                callback(true)
            }
            .addOnFailureListener { exception ->
                println("Error uploading user: ${exception.message}")
                callback(false)
            }
    }

    fun editPost(post: PostEntity, callback: (Boolean) -> Unit){
        val db = Firebase.firestore
        val postDocRef = db.collection("posts").document(post.pid)
        val updatedPostData = hashMapOf(
            "type" to post.type,
            "cpu" to post.cpu,
            "gpu" to post.gpu,
            "motherboard" to post.motherboard,
            "memory" to post.memory,
            "ram" to post.ram,
            "postImage" to post.postImage
        )
        postDocRef.update(updatedPostData as Map<String, Any>)
            .addOnSuccessListener {
                println("Post updated successfully")
                callback(true)
            }
            .addOnFailureListener{e->
                println("Error updating post: ${e.message}")
                callback(false)
            }
    }

    fun deletePostFromFirebase(post: PostEntity, callback: (Boolean) -> Unit) {
        val id = post.pid
        db.collection("posts").document(id)
            .delete()
            .addOnSuccessListener {
                callback(true) // Successful deletion from Firebase
            }
            .addOnFailureListener { exception ->
                callback(false) // Handle deletion failure
                println("Error deleting post with ID: $id. Exception: $exception")
            }
    }
    fun getAllPosts(callback: (List<PostEntity>) -> Unit) {
        val query: Query = db.collection("posts")

        query.get().addOnCompleteListener{snapshot->
            if(snapshot.isSuccessful){
                val list = LinkedList<PostEntity>()
                val doc = snapshot.result

                for(postMap in doc){
                    val post = PostEntity(postMap.id, "", "", "", "", "", "", "","")
                    post.fromMap(postMap.data)


                    list.add(post)
                }

                callback(list)
            }
        }
    }



    fun getPostsByUserId(uid: String, callback: (List<PostEntity>) -> Unit) {
        val db = Firebase.firestore
        val postsCollection = db.collection("posts")

        postsCollection.whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val list = ArrayList<PostEntity>()
                    for (document in result!!) {
                        val post=PostEntity(document.id,"", "", "", "", "", "", "","")
                        post.fromMap(document.data)
                        list.add(post)

                    }
                    callback(list)
                } else {
                    println("No document found with UID $uid")
                    callback(emptyList())
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching user document: ${exception.message}")
                callback(emptyList())
            }
    }
}