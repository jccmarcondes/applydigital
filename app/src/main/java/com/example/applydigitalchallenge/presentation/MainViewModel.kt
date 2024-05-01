package com.example.applydigitalchallenge.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.applydigitalchallenge.data.local.PostDao
import com.example.applydigitalchallenge.data.model.PostEntity
import com.example.applydigitalchallenge.domain.AGetPostsUseCase
import com.example.applydigitalchallenge.presentation.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * MainViewModel is a ViewModel class responsible for managing the data and business logic related to the main screen.
 * It interacts with use cases, repository, and database to fetch and manipulate post data.
 *
 * @param useCase The use case responsible for fetching posts.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: AGetPostsUseCase
) : ViewModel() {

    // MutableStateFlow to hold the list of posts
    private val _posts = MutableStateFlow(mutableListOf<Post>())
    // Exposed StateFlow to observe the list of posts
    val posts: StateFlow<MutableList<Post>> get() = _posts

    // MutableStateFlow to hold the list of revealed card IDs
    private val _revealedCardIdsList = MutableStateFlow(listOf<Long>())
    // Exposed StateFlow to observe the list of revealed card IDs
    val revealedCardIdsList: StateFlow<List<Long>> get() = _revealedCardIdsList

    // MutableStateFlow to manage the refreshing state
    private val _isRefreshing = MutableStateFlow(false)
    // Exposed StateFlow to observe the refreshing state
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /**
     * Fetches posts from the repository and updates the list of posts.
     *
     * @param postDao The PostDao instance for database operations.
     * @param stringSetState The state holder for managing a set of string values.
     */
    fun fetchPosts(postDao: PostDao, stringSetState: SnapshotStateList<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.value = true

            useCase().onSuccess { response ->
                // Process the response and update the list of posts
                // Also, update the database with new post entries
                val hits = response
                    .hits
                    .sortedByDescending { it.updatedAt }
                    .distinctBy { it.storyId }

                postDao.deleteAllPosts()

                val posts = arrayListOf<Post>()

                hits.forEach { hit ->
                    with(hit) {
                        storyId?.let { storyId ->
                            val found = stringSetState.find { it.toLong() == storyId }
                            found?:run {
                                when {
                                    title != null ->
                                        {
                                            posts += Post(
                                                id = storyId,
                                                title = title,
                                                url = url,
                                                author = author,
                                                createdAt = createdAt
                                            )

                                            title?.let { title ->
                                                postDao.insert(
                                                    PostEntity(
                                                        postId = storyId,
                                                        title = title,
                                                        url = url,
                                                        author = author,
                                                        createdAt = createdAt
                                                    )
                                                )
                                            }
                                            return@with
                                        }

                                    storyTitle != null ->
                                        {
                                            posts += Post(
                                                id = storyId,
                                                title = storyTitle,
                                                url = storyUrl,
                                                author = author,
                                                createdAt = createdAt
                                            )

                                            storyTitle?.let { storyTitle ->
                                                postDao.insert(
                                                    PostEntity(
                                                        postId = storyId,
                                                        title = storyTitle,
                                                        url = storyUrl,
                                                        author = author,
                                                        createdAt = createdAt
                                                    )
                                                )
                                            }
                                            return@with
                                        }

                                    else -> {}
                                }
                            }
                        }
                    }
                }
                _posts.emit(posts)
            }.onFailure {
                // Handle failure scenarios, like logging the error
                it.printStackTrace()
            }

            _isRefreshing.value = false
        }
    }

    /**
     * Fetches posts from the local database and updates the list of posts.
     *
     * @param postDao The PostDao instance for database operations.
     */
    fun fetchPostsOffline(postDao: PostDao) {
        // Fetch posts from the local database and update the list of posts
        val posts = arrayListOf<Post>()

        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.value = true

            postDao.getAll().forEach { entity ->
                posts += Post(
                    id = entity.postId,
                    title = entity.title,
                    url = entity.url,
                    author = entity.author,
                    createdAt = entity.createdAt
                )
            }
            _posts.emit(posts)

            _isRefreshing.value = false
        }
    }

    /**
     * Removes a post from the list of posts and updates the database.
     *
     * @param postDao The PostDao instance for database operations.
     * @param stringSetState The state holder for managing a set of string values.
     * @param post The post to be removed.
     */
    fun onItemRemoved(
        postDao: PostDao,
        stringSetState: SnapshotStateList<String>,
        post: Post
    ) {
        // Remove the post from the list, update the database, and manage the set of string values
        if (!_posts.value.contains(post)) return
        onItemCollapsed(post.id)
        stringSetState.add(post.id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            postDao.delete(postDao.getItem(post.id))
        }
        _posts.value = _posts.value.toMutableList().also { list ->
            list.remove(post)
        }
    }

    /**
     * Marks a card as expanded by adding its ID to the list of revealed card IDs.
     *
     * @param cardId The ID of the card to be marked as expanded.
     */
    fun onItemExpanded(cardId: Long) {
        // Mark the card as expanded by adding its ID to the list of revealed card IDs
        if (_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(cardId)
        }
    }

    /**
     * Marks a card as collapsed by removing its ID from the list of revealed card IDs.
     *
     * @param cardId The ID of the card to be marked as collapsed.
     */
    fun onItemCollapsed(cardId: Long) {
        // Mark the card as collapsed by removing its ID from the list of revealed card IDs
        if (!_revealedCardIdsList.value.contains(cardId)) return
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(cardId)
        }
    }
}
