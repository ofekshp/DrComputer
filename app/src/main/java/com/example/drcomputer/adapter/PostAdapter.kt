package com.example.drcomputer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.models.Post

class MyRvAdapter(private val data:List<Post>): RecyclerView.Adapter<MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder:MyViewHolder,position:Int)
{
        val post=data[position]
        holder.title?.text=post.title
        holder.subtitle?.text=post.subtitle
    }
}