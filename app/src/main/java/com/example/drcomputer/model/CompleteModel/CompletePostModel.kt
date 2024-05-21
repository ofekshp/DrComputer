package com.example.drcomputer.model.CompleteModel

import androidx.lifecycle.MutableLiveData
import com.example.drcomputer.GetDrComputer
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.model.firebase.PostFB
import com.example.drcomputer.model.room.PostModel
import com.google.firebase.auth.FirebaseAuth
import java.util.LinkedList


class CompletePostModel {
    companion object {
        var instance: CompletePostModel = CompletePostModel()
    }

    private val modelFirebase = PostFB()
    private val modelRoom = PostModel()
    private val allPosts = AllPostLiveData()
    private val myPosts=AllPostLiveData()

    fun getAllPosts(): AllPostLiveData{
        return allPosts
    }
    fun uploadPost(post: PostEntity, callback: (Boolean) -> Unit){
        modelFirebase.uploadPost(post){isSuccessful ->
            if(isSuccessful){
                // Update the post in the local database
                GetDrComputer.getExecutorService().execute {
                    modelRoom.insertPost(post)
                 GetDrComputer.getExecutorService()
                }
            }

            callback(isSuccessful)
        }

    }
    fun deletePost(post: PostEntity, callback: (Boolean) -> Unit) {
        // Delete from Firebase
        modelFirebase.deletePostFromFirebase(post) { isSuccessful ->
            if (isSuccessful) {
                // If deletion from Firebase was successful, delete from local database
                GetDrComputer.getExecutorService().execute {
                        modelRoom.deletePost(post)
                }
            } else {
                // Handle Firebase deletion failure (optional)
            }

            callback(isSuccessful)
        }
    }



    fun getPostsByUid(uid: String): MutableLiveData<List<PostEntity>> {

        val postsLiveData = MutableLiveData<List<PostEntity>>()
        GetDrComputer.getExecutorService().execute {
            val postsByUid = modelRoom.getPostsByUid(uid)
            GetDrComputer.getExecutorService().execute {
                postsLiveData.postValue(postsByUid)
            }
        }

        modelFirebase.getPostsByUserId(uid) { posts: List<PostEntity> ->
            postsLiveData.postValue(posts)
            // insert into Room
            GetDrComputer.getExecutorService().execute {
                for (post in posts) {
                    modelRoom.insertPost(post)
                }
            }
        }
        return postsLiveData
    }

    inner class AllPostLiveData: MutableLiveData<List<PostEntity>>() {
        init{
            value = LinkedList<PostEntity>()

        }

        override fun onActive() {
            super.onActive()

            GetDrComputer.getExecutorService().execute{
                val allPosts = modelRoom.getAllPosts()
                postValue(allPosts)
            }

            modelFirebase.getAllPosts{ posts : List<PostEntity> ->
                value = posts

                GetDrComputer.getExecutorService().execute {
                    for (post in posts) {
                        modelRoom.insertPost(post)
                    }
                }
            }
        }
    }
}