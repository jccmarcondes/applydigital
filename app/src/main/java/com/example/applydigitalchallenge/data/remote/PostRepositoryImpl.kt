package com.example.applydigitalchallenge.data.remote

import com.example.applydigitalchallenge.data.model.HackerNewsData
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val source: PostRemoteSource
): PostRepository {

    override suspend fun getPosts(): Result<HackerNewsData> =
        Result.success(source.getPosts())
}