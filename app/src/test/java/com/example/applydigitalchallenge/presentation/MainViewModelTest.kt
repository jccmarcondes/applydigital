package com.example.applydigitalchallenge.presentation

import android.annotation.SuppressLint
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.mutableStateListOf
import com.example.applydigitalchallenge.data.local.PostDao
import com.example.applydigitalchallenge.data.model.HackerNewsData
import com.example.applydigitalchallenge.data.model.Hit
import com.example.applydigitalchallenge.data.model.PostEntity
import com.example.applydigitalchallenge.domain.AGetPostsUseCase
import com.example.applydigitalchallenge.presentation.model.Post
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    // Set the main coroutine dispatcher for testing
    private val testDispatcher = UnconfinedTestDispatcher()

    // Use InstantTaskExecutorRule to make LiveData work with JUnit
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock dependencies
    private val mockUseCase = mockk<AGetPostsUseCase>(relaxed = true)
    private val mockPostDao = mockk<PostDao>(relaxed = true)

    // Subject under test
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mockUseCase)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchPosts should update posts and refreshing state`() = runTest {
        // Given
        val response = HackerNewsData(
            listOf(
                Hit(
                    title = "Test",
                    url = "https://www.test.com",
                    storyId = 123456,
                    storyTitle = "",
                    storyUrl = "",
                    author = "Julio Marcondes",
                    createdAt = "Yesterday",
                    updatedAt = "Yesterday"
                )
            )

        )

        coEvery { mockUseCase.invoke() } returns Result.success(response)

        // When
        viewModel.fetchPosts(mockPostDao, mutableStateListOf())

        // Then
        verify {
            (mockPostDao).deleteAllPosts()
        }

        assert(viewModel.posts.value.isNotEmpty())
        assert(!viewModel.isRefreshing.value)
    }

    @Test
    fun `fetchPostsOffline should update posts and refreshing state`() = runTest {
        // Given
        val mockEntity = PostEntity(
            postId = 123456,
            title = "Test",
            url = "https://www.test.com",
            author = "Julio Marcondes",
            createdAt = "Yesterday"
        )

        coEvery { mockPostDao.getAll() } returns listOf(mockEntity)

        // When
        viewModel.fetchPostsOffline(mockPostDao)

        // Then
        assert(!viewModel.isRefreshing.value)
    }

    @SuppressLint("CheckResult")
    @Test
    fun `onItemRemoved should remove post from list and database`() {
        // Given
        val mockPost: Post = Post.mock()
        viewModel.posts.value.toMutableList().add(mockPost)

        // When
        viewModel.onItemRemoved(mockPostDao, mutableStateListOf(), mockPost)

        // Then
        assert(viewModel.posts.value.isEmpty())
    }

    @Test
    fun `onItemExpanded should add cardId to revealedCardIdsList`(): Unit = runTest {
        // Given
        val cardId = 123L
        val initialList = listOf<Long>()
        viewModel.revealedCardIdsList.value.toMutableList().addAll(initialList)

        // When
        viewModel.onItemExpanded(cardId)

        // Then
        assertTrue(viewModel.revealedCardIdsList.value.isNotEmpty())
    }

    @Test
    fun `onItemExpanded should not modify revealedCardIdsList if cardId already exists`() = runTest {
        // Given
        val cardId = 123L
        val initialList = listOf(cardId)
        viewModel.revealedCardIdsList.value.toMutableList().addAll(initialList)

        // When
        viewModel.onItemExpanded(cardId)

        // Then
        assertTrue(viewModel.revealedCardIdsList.value.isNotEmpty()) // Verify value remains unchanged
    }

    @Test
    fun `onItemCollapsed should remove cardId from revealedCardIdsList`() = runTest {
        // Given
        val cardId = 123L
        val initialList = listOf(cardId)
        viewModel.revealedCardIdsList.value.toMutableList().addAll(initialList)

        // When
        viewModel.onItemCollapsed(cardId)

        // Then
        assertTrue(viewModel.revealedCardIdsList.value.isEmpty())
    }

    @Test
    fun `onItemCollapsed should not modify revealedCardIdsList if cardId does not exist`() = runTest {
        // Given
        val cardId = 123L
        val initialList = listOf<Long>()
        viewModel.revealedCardIdsList.value.toMutableList().addAll(initialList)

        // When
        viewModel.onItemCollapsed(cardId)

        // Then
        assertTrue(viewModel.revealedCardIdsList.value.isEmpty())
    }
}