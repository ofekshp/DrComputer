package com.example.drcomputer.model.api

import com.example.drcomputer.adapter.NewsResponse
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import okhttp3.Request
import com.google.gson.Gson


class newsApiService
{
    private val baseUrl="https://newsapi.org/v2/"
     fun makeCall(callback: (Boolean) -> Unit):NewsResponse? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val today: Date = Calendar.getInstance().time
        val formattedDate: String = dateFormat.format(today)
        val apiKey=ApiKey().getKey()
        val type="everything"
        val query1="q=computer"
        val query2= "from=$formattedDate"
        val url= "$baseUrl$type/?$query1&apiKey=$apiKey"

        val client=OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(url)
            .build()

           return try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    callback(response.isSuccessful)
                }
                val gson=Gson()
                val responseBody = response.body?.string()
                gson.fromJson(responseBody, NewsResponse::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }


    }
}

