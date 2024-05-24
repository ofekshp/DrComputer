package com.example.drcomputer.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController


import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.fragments.MyPostsDirections
import com.example.drcomputer.fragments.homePageDirections
import com.example.drcomputer.model.entities.PostEntity


class MyPostsAdapter(  private val navController: NavController, private val contextType: String) : RecyclerView.Adapter<MyPosts>()
{
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
            navController.navigate(action)
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