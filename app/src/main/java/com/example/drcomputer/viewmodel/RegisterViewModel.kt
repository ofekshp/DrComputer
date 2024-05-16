package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.completeUserModel

class RegisterUserViewModel: ViewModel() {
    val UserModel = completeUserModel()

    fun register(userName:String,email : String, password: String,  callback: (Boolean) -> Unit){
        UserModel.register(userName,email, password, callback)
    }
}