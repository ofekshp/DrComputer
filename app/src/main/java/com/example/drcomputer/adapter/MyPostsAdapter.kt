package com.example.drcomputer.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController


import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.fragments.MyPostsDirections
import com.example.drcomputer.fragments.homePageDirections
import com.example.drcomputer.model.entities.PostEntity
import com.google.firebase.auth.FirebaseAuth




class MyPostsAdapter(
    private val navController: NavController,
    private val contextType: String,
    private val context: Context?
) : RecyclerView.Adapter<MyPosts>()
{
    private var auth=FirebaseAuth.getInstance()
    private var posts = arrayListOf<PostEntity>()
    private var listener:OnItemClickListener?=null
    fun setOnItemClickListener(listener:OnItemClickListener)
    {
        this.listener=listener
    }
    fun onClick(position: Int)
    {
        listener?.onItemClick(posts[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): MyPosts {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return MyPosts(itemView,this)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder:MyPosts,position:Int)
    {
        val post=posts[position]
        holder.type?.text=post.type
        holder.cpu?.text=post.cpu
        holder.gpu?.text=post.gpu
        holder.motherboard?.text=post.motherboard
        holder.ram?.text=post.ram
        holder.memory?.text=post.memory
        listener?.onItemClick(post)
        holder.itemView.setOnClickListener {
            val action = when (contextType) {
                //TODO change direction from homepage
                "HOME" -> homePageDirections.actionHomePageToEditPost(post)
                "MY_POSTS" -> MyPostsDirections.actionMyPostsToEditPost(post)
                else -> throw IllegalArgumentException("Unknown context type")
            }
            if(post.uid.equals(auth.currentUser!!.uid))
                navController.navigate(action)
            else
                Toast.makeText(context, "Can't edit a post that isn't yours", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newPosts: ArrayList<PostEntity>) {
        posts = newPosts
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(post:PostEntity)
    }
}