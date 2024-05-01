package com.example.applydigitalchallenge.domain

import com.example.applydigitalchallenge.data.remote.PostRepository
import com.example.applydigitalchallenge.data.model.HackerNewsData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

class GetPostsUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository,
    coroutineDispatcher: CoroutineDispatcher
): AGetPostsUseCase(coroutineDispatcher) {

    override suspend fun execute(param: Nothing?): Result<HackerNewsData> =
        postRepository.getPosts()
}