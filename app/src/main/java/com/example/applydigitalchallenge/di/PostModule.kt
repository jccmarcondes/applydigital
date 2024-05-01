package com.example.applydigitalchallenge.di

import com.example.applydigitalchallenge.data.remote.PostRemoteSource
import com.example.applydigitalchallenge.data.remote.PostRepository
import com.example.applydigitalchallenge.data.remote.PostRepositoryImpl
import com.example.applydigitalchallenge.domain.AGetPostsUseCase
import com.example.applydigitalchallenge.domain.GetPostsUseCaseImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@dagger.Module
@InstallIn(SingletonComponent::class)
class PostModule {

    @Singleton
    @Provides
    internal fun providesPostRepository(postRemoteSource: PostRemoteSource): PostRepository =
        PostRepositoryImpl(postRemoteSource)

    @Singleton
    @Provides
    internal fun providesGetPostsUseCaseImpl(postRepository: PostRepository): AGetPostsUseCase =
        GetPostsUseCaseImpl(
            postRepository,
            Dispatchers.IO
        )
}