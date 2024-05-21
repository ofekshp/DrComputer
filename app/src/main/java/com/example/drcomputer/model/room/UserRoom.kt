package com.example.drcomputer.model.room

import com.example.drcomputer.model.entities.UserEntity

class UserRoom {
    fun insert(user: UserEntity){ 
        AppLocalDB.getInstance().userDao().insert(user)
    }
    fun getUserById(uid: String): UserEntity {
        return AppLocalDB.getInstance().userDao().getUserById(uid)
    }

    fun editUser(user: UserEntity){
        return AppLocalDB.getInstance().userDao().updateUser(user)
    }
}