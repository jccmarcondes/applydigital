package com.example.applydigitalchallenge.data.remote

import com.example.applydigitalchallenge.data.model.HackerNewsData
import retrofit2.http.GET

interface PostRemoteSource {
    @GET("search_by_date?query=mobile")
    suspend fun getPosts(): HackerNewsData
}