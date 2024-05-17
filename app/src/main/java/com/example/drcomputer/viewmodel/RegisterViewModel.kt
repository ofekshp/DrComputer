package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.completeUserModel
import com.example.drcomputer.model.entities.UserEntity

class RegisterUserViewModel: ViewModel() {
    val UserModel = completeUserModel()

    fun register(user:UserEntity, password: String,  callback: (Boolean) -> Unit){
        UserModel.register(user, password, callback)
    }
}