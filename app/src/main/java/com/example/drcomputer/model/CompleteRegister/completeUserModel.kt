package com.example.drcomputer.model.CompleteRegister

import com.example.drcomputer.model.firebase.UserFB
import com.google.firebase.auth.FirebaseAuth

class completeUserModel {
    private val firebaseModel = UserFB()
    private lateinit var auth: FirebaseAuth

    fun register(userName:String,email:String,password: String,callback: (Boolean) -> Unit ){
        auth = FirebaseAuth.getInstance()
        firebaseModel.register(email,password){ isSuccessful ->
            if(isSuccessful){
                //val uid =auth.currentUser?.uid
                firebaseModel.userCollection(userName, email){success->
                    callback(success)
                }
            }
            else
                callback(false)
        }

    }
}