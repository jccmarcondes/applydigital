package com.example.applydigitalchallenge.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.applydigitalchallenge.data.local.PostDao
import com.example.applydigitalchallenge.data.local.PreferenceKeys
import com.example.applydigitalchallenge.data.local.rememberStringSetPreference
import com.example.applydigitalchallenge.presentation.MainViewModel
import com.example.applydigitalchallenge.presentation.callbacks.WebViewCallback
import com.example.applydigitalchallenge.presentation.components.PostList
import com.example.applydigitalchallenge.util.isNetworkAvailable

/**
 * MainScreen is a composable function that represents the main screen of the application.
 * It displays a list of posts in a Scaffold layout, with support for pull-to-refresh functionality.
 *
 * @param context The context used for checking network availability and other Android system operations.
 * @param viewModel The MainViewModel instance used to manage the main screen's data and business logic.
 * @param postDao The PostDao instance used for database operations related to posts.
 * @param webViewCallback The callback interface for opening a web view to display the full post content.
 */
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "MutableCollectionMutableState")
@Composable
fun MainScreen(
    context: Context,
    viewModel: MainViewModel,
    postDao: PostDao,
    webViewCallback: WebViewCallback
) {
    val scaffoldState = rememberScaffoldState()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val stringSetState = rememberStringSetPreference(
        key = PreferenceKeys.OLD_CARD_ID_LIST_KEY,
        defaultValue = setOf("0")
    )
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            if (isNetworkAvailable(context)) {
                viewModel.fetchPosts(postDao, stringSetState)
            } else {
                viewModel.fetchPostsOffline(postDao)
            }
        }
    )

    LaunchedEffect(Unit) {
        if (isNetworkAvailable(context)) {
            viewModel.fetchPosts(postDao, stringSetState)
        } else {
            viewModel.fetchPostsOffline(postDao)
        }
    }

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                PostList(
                    viewModel,
                    postDao,
                    webViewCallback,
                    stringSetState,
                    paddingValues)
            }

            PullRefreshIndicator(
                isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}