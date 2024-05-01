package com.example.applydigitalchallenge.domain

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Base class for a Use Case.
 * This interface represents an execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * @param P the request type
 * @param R the response type
 */

abstract class BaseUseCase<P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    abstract suspend fun execute(param: P? = null): Result<R>
    suspend operator fun invoke(param: P? = null): Result<R> {
        return withContext(coroutineDispatcher) {
            execute(param)
        }
    }
}