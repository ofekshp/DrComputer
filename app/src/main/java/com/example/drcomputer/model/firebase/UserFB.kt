package com.example.drcomputer.model.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFB {
    private lateinit var auth: FirebaseAuth

    fun register(email: String, password: String, callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    val exception = task.exception
                    println("Registration failed: ${exception?.message}")
                    callback(false)
                }
            }
    }

    fun userCollection(
        userName: String,
        email: String,
        //uid: String,
        callback: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        val data = hashMapOf(
            "userName" to userName,
            "email" to email
            //"uid" to uid
        )
        db.collection("users").add(data).addOnSuccessListener {
            println("Post uploaded successfully")
            callback(true)
        }.addOnFailureListener { exception ->
            println("Error uploading user: ${exception.message}")
            callback(false)
        }
    }

//    fun getUserByUid(uid: String, callback: (UserEntity?) -> Unit) {
//        val db = Firebase.firestore
//        val usersCollection = db.collection("users")
//
//        usersCollection.whereEqualTo("uid", uid)
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                if (!querySnapshot.isEmpty) {
//                    val documentSnapshot = querySnapshot.documents.first()
//                    val userName = documentSnapshot.getString("name") ?: ""
//                    val email = documentSnapshot.getString("email") ?: ""
//                    val profileImg = documentSnapshot.getString("img") ?: ""
//
//                    val userEntity = UserEntity(
//                        userMame = userName,
//                        uid = uid,
//                        email = email
//                    )
//
//                    callback(userEntity)
//                } else {
//                    println("No document found with UID $uid")
//                    callback(null)
//                }
//            }
//            .addOnFailureListener { exception ->
//                println("Error fetching user document: ${exception.message}")
//                callback(null)
//            }
//    }

}

