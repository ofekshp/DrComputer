package com.example.drcomputer.adapter

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)
data class myArticle(
    val title:String,
    val url:String
)
data class Source(
    val id: String?,
    val name: String
)