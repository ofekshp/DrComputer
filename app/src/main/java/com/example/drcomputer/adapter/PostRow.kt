package com.example.drcomputer.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var type: TextView?=null
    var cpu: TextView?=null
    var gpu: TextView?=null
    var motherboard: TextView?=null
    var memory: TextView?=null
    var ram: TextView?=null


    init {
        type= itemView.findViewById<TextView>(R.id.type)
        cpu=itemView.findViewById<TextView>(R.id.cpu)
        gpu= itemView.findViewById<TextView>(R.id.gpu)
        motherboard= itemView.findViewById<TextView>(R.id.motherboard)
        memory= itemView.findViewById<TextView>(R.id.memory)
        ram= itemView.findViewById<TextView>(R.id.ram)
    }
}