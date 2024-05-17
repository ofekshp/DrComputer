package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.completeUserModel
import com.example.drcomputer.model.entities.UserEntity

class ProfileViewModel: ViewModel() {
    val usersModel = completeUserModel()

    fun getUserByUid(uid :String,  callback: (UserEntity)-> Unit){
        return usersModel.getUserByUid(uid, callback)
    }
}