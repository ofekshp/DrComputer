package com.example.drcomputer.model.firebase

import com.example.drcomputer.model.entities.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFB {
    private lateinit var auth: FirebaseAuth

    fun register(user:UserEntity, password: String, callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    fun userCollection(
        userName: String,
        email: String,
        uid: String,
        profileImg:String,
        callback: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        val data = hashMapOf(
            "userName" to userName,
            "email" to email,
            "uid" to uid,
            "profileImg" to profileImg
        )
        db.collection("users").document(uid).set(data)
            .addOnSuccessListener {
                println("User uploaded successfully with UID: $uid")
                callback(true)
            }
            .addOnFailureListener { exception ->
                println("Error uploading user: ${exception.message}")
                callback(false)
            }
    }

    fun getUserByUid(uid: String, callback: (UserEntity?) -> Unit) {
        val db = Firebase.firestore
        val usersCollection = db.collection("users")

        usersCollection.whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val docResult = result.documents.first()
                    val name = docResult.getString("userName") ?: ""
                    val email = docResult.getString("email") ?: ""
                    val profileImg = docResult.getString("profileImg") ?: ""

                    val userEntity = UserEntity(
                        userName = name,
                        uid = uid,
                        email = email,
                        profileImg = profileImg
                    )
                    callback(userEntity)
                } else {
                    println("No document found with UID $uid")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                println("Error fetching user document: ${exception.message}")
                callback(null)
            }
    }

    fun editProfile(user: UserEntity, password: String, callback: (Boolean) -> Unit) {
        val db = Firebase.firestore
        val usersCollection = db.collection("users")
        auth = Firebase.auth
        usersCollection.whereEqualTo("uid", user.uid)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val docResult = result.documents.first()
                    var userName = docResult.getString("userName") ?: ""
                    var email = docResult.getString("email") ?: ""
                    var profileImg = docResult.getString("profileImg") ?: ""

                    if (profileImg != user.profileImg) {
                        profileImg = user.profileImg
                        db.collection("users").document(user.uid)
                            .update("profileImg", profileImg)
                    }

                    if (userName != user.userName) {
                        userName = user.userName
                        db.collection("users").document(user.uid)
                            .update("userName", userName)
                            .addOnSuccessListener {
                                println("update userName success")
                            }
                            .addOnFailureListener{e->
                                println("update userName failed: ${e.message}")
                            }
                    }
                    if (email != user.email) {
                        email = user.email
                        val current = auth.currentUser
                        if (current != null) {
                            updateEmail(current, email) { isSuccessful ->
                                if (isSuccessful) {
                                    println("update email in auth success")
                                } else {
                                    println("failed update email in auth")
                                    callback(false)
                                }
                            }
                        }
                    }
                    if (password != ""&& password.isNotEmpty()) {
                        val current = auth.currentUser
                        if (current != null) {
                            updatePassword(current, password) { isSuccessful ->
                                if (isSuccessful) {
                                    println("update password in auth success")
                                } else {
                                    println("failed update password in auth")
                                    callback(false)
                                }
                            }
                        }
                    }

                    val updatedData = hashMapOf(
                        "userName" to userName,
                        "email" to email,
                        "uid" to user.uid,
                        "profileImg" to profileImg
                    )
                    updateUserProfile(docResult.id, updatedData, callback)
                }
            }
    }
    private fun updateEmail(currentUser: FirebaseUser, newEmail: String, callback: (Boolean) -> Unit) {
        currentUser.verifyBeforeUpdateEmail(newEmail).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    private fun updatePassword(currentUser: FirebaseUser, newPassword: String, callback: (Boolean) -> Unit) {
        currentUser.updatePassword(newPassword)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
    private fun updateUserProfile(
        documentId: String,
        updatedData: Map<String, Any>,
        callback: (Boolean) -> Unit
    ) {
        val db = Firebase.firestore
        val userRef = db.collection("users").document(documentId)

        userRef.update(updatedData)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

}

