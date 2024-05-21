package com.example.drcomputer.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView?=null
    var subtitle: TextView?=null
    var imageView:ImageView?=null

    init {
        title= itemView.findViewById<TextView>(R.id.title)
        subtitle=itemView.findViewById<TextView>(R.id.subtitle)
    }
}