package com.example.drcomputer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drcomputer.GetDrComputer
import com.example.drcomputer.R
import com.example.drcomputer.adapter.Article
import com.example.drcomputer.adapter.ArticleAdapter
import com.example.drcomputer.adapter.myArticle
import com.example.drcomputer.model.api.newsApiService


class Articles : Fragment() {
    private lateinit var articles: ArrayList<myArticle>
    private lateinit var adapter: ArticleAdapter
    private lateinit var newRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_articles, container, false)
        articles = arrayListOf()


        newRecyclerView = view.findViewById(R.id.articleList)
        newRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        newRecyclerView.setHasFixedSize(true)
        adapter = ArticleAdapter()
        newRecyclerView.adapter = adapter
        fetchArticles()
        return view
    }

    private fun fetchArticles() {
        GetDrComputer.getExecutorService().execute {
            val call = newsApiService().makeCall { success ->
                println(success)
            }
            if (call != null) {
                for (article in call.articles) {
                    val title = article.title.toString()
                    val url = article.url.toString()
                    val myArticle = myArticle(title, url)
                    articles.add(myArticle)
                }
                activity?.runOnUiThread {
                    adapter.submitList(articles)

                }
            }
        }
    }
}