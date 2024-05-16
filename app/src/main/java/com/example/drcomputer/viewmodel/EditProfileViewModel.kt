package com.example.drcomputer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.drcomputer.model.CompleteModel.completeUserModel
import com.example.drcomputer.model.entities.UserEntity

class EditProfileViewModel: ViewModel() {
    val usersModel = completeUserModel()

    fun editProfile(user: UserEntity,password:String,  callback: (Boolean)-> Unit){
        return usersModel.editProfile(user,password, callback)
    }
}