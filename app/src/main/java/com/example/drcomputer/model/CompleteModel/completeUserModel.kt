package com.example.drcomputer.model.CompleteModel

import com.example.drcomputer.model.entities.UserEntity
import com.example.drcomputer.model.firebase.UserFB
import com.google.firebase.auth.FirebaseAuth

class completeUserModel {
    private val firebaseModel = UserFB()
    private lateinit var auth: FirebaseAuth

    fun register(user: UserEntity, password: String, callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance()
        firebaseModel.register(user.email, password) { isSuccessful ->
            if (isSuccessful) {
                val uid = auth.currentUser?.uid
                firebaseModel.userCollection(user.userName, user.email, uid.toString()) { success ->
                    callback(success)
                }
            } else
                callback(false)
        }

    }

    fun getUserByUid(uid: String, callback: (UserEntity) -> Unit) {
        firebaseModel.getUserByUid(uid) { userEntity ->
            if (userEntity != null)
                callback(userEntity)
        }
    }

    fun editProfile(user: UserEntity, password: String,callback: (Boolean) -> Unit ){
        firebaseModel.editProfile(user,password){isSuccessful ->
            callback(isSuccessful)
        }
    }

}