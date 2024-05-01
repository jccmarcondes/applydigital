package com.example.applydigitalchallenge.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.applydigitalchallenge.data.local.PostDao
import com.example.applydigitalchallenge.presentation.MainViewModel
import com.example.applydigitalchallenge.presentation.callbacks.WebViewCallback
import com.example.applydigitalchallenge.util.POST_OFFSET
import com.example.applydigitalchallenge.util.dp


/**
 * PostList is a composable function that represents a list of posts displayed in a vertical LazyColumn.
 * Each post in the list is represented by a DraggablePost composable, allowing users to interact with the posts.
 *
 * @param viewModel The MainViewModel instance used to retrieve and manage the post data.
 * @param postDao The PostDao instance used for database operations related to posts.
 * @param webViewCallback The callback interface for opening a web view to display the full post content.
 * @param stringSetState The state holder for managing a set of string values.
 * @param paddingValues The padding values to apply to the PostList composable.
 */
@Composable
fun PostList(
    viewModel: MainViewModel,
    postDao: PostDao,
    webViewCallback: WebViewCallback,
    stringSetState: SnapshotStateList<String>,
    paddingValues: PaddingValues
) {
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val revealedCardIds by viewModel.revealedCardIdsList.collectAsStateWithLifecycle()

    LazyColumn(Modifier.padding(paddingValues)) {
        items(posts) { post ->
            val movableContent = movableContentOf {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 1.dp)
                ) {
                    ActionRow(
                        action = {
                            viewModel.onItemRemoved(
                                postDao = postDao,
                                stringSetState = stringSetState,
                                post = post
                            )
                        }
                    )
                    DraggablePost(
                        webViewCallback = webViewCallback,
                        post = post,
                        isRevealed = revealedCardIds.contains(post.id),
                        postOffset = POST_OFFSET.dp(),
                        onExpand = { viewModel.onItemExpanded(post.id) },
                        onCollapse = { viewModel.onItemCollapsed(post.id) },
                    )
                }
            }

            movableContent()
        }
    }
}