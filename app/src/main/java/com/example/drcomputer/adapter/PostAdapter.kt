package com.example.drcomputer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity

class PostAdapter(private val posts:ArrayList<PostEntity>): RecyclerView.Adapter<MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return posts.size
    }

    override fun onBindViewHolder(holder:MyViewHolder,position:Int)
{
        val post=posts[position]
        holder.type?.text=post.type
        holder.cpu?.text=post.cpu
        holder.gpu?.text=post.gpu
        holder.motherboard?.text=post.motherboard
        holder.ram?.text=post.ram
        holder.memory?.text=post.memory

    }
}