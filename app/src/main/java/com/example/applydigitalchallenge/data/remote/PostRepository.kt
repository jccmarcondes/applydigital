package com.example.applydigitalchallenge.data.remote

import com.example.applydigitalchallenge.data.model.HackerNewsData

interface PostRepository {
    suspend fun getPosts(): Result<HackerNewsData>
}