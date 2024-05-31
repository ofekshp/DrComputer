package com.example.drcomputer.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.R
import com.example.drcomputer.model.entities.PostEntity

class ArticleAdapter  : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var articles = ArrayList<myArticle>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)

        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView?.text = article.title
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(article.url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount():Int
    {
        return articles.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun submitList(articleList: ArrayList<myArticle>) {
        articles = articleList
        notifyDataSetChanged()
    }
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null

        init {
            titleTextView = itemView.findViewById(R.id.articleTitle)
        }
    }
}
