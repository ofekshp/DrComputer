package com.example.drcomputer.model.CompleteModel

import com.example.drcomputer.GetDrComputer
import com.example.drcomputer.model.entities.UserEntity
import com.example.drcomputer.model.firebase.UserFB
import com.example.drcomputer.model.room.UserRoom
import com.google.firebase.auth.FirebaseAuth

class completeUserModel {
    private val firebaseModel = UserFB()
    private val roomModel = UserRoom()
    private lateinit var auth: FirebaseAuth

    fun register(user: UserEntity,password: String, callback: (Boolean) -> Unit) {
        auth = FirebaseAuth.getInstance()
        firebaseModel.register(user,password) { isSuccessful ->
            if (isSuccessful) {
                val uid = auth.currentUser?.uid
                firebaseModel.userCollection(user.userName, user.email, uid.toString()) { isSuccess ->
                    if(isSuccess)
                    {
                        GetDrComputer.getExecutorService().execute{
                            if (uid != null) {
                                user.uid=uid
                                roomModel.insert(user)
                            }
                        }
                    }
                    callback(isSuccess)
                }
            } else
                callback(false)
        }

    }

    fun getUserByUid(uid: String, callback: (UserEntity) -> Unit) {
        firebaseModel.getUserByUid(uid) { userEntity ->
            if (userEntity != null)
            {
                GetDrComputer.getExecutorService().execute{
                    roomModel.getUserById(uid)
                }
                callback(userEntity)
            }
        }
    }

    fun editProfile(user: UserEntity, password: String,callback: (Boolean) -> Unit ){
        firebaseModel.editProfile(user,password){isSuccessful ->
            if(isSuccessful)
            {
                GetDrComputer.getExecutorService().execute{
                    roomModel.editUser(user)
                }
            }
            callback(isSuccessful)
        }
    }

}