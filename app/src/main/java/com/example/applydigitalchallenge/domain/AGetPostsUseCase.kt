package com.example.applydigitalchallenge.domain

import com.example.applydigitalchallenge.data.model.HackerNewsData
import kotlinx.coroutines.CoroutineDispatcher

abstract class AGetPostsUseCase(coroutineDispatcher: CoroutineDispatcher) :
    BaseUseCase<Nothing, HackerNewsData>(
        coroutineDispatcher
    )