package com.example.applydigitalchallenge.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.example.applydigitalchallenge.data.local.AppDatabase
import com.example.applydigitalchallenge.presentation.callbacks.WebViewCallback
import com.example.applydigitalchallenge.presentation.screens.MainScreen
import com.example.applydigitalchallenge.util.APP_DATABASE_NAME
import com.example.applydigitalchallenge.util.URL_PARAMETER_REFERENCE
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity is the main entry point of the Android application.
 * It extends ComponentActivity and serves as the host for various UI components and functionalities.
 * The activity is annotated with @AndroidEntryPoint for Hilt dependency injection.
 * It also implements the WebViewCallback interface for handling WebView interactions.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity(), WebViewCallback {
    // View model instance provided by Hilt dependency injection
    private val viewModel by viewModels<MainViewModel>()

    // Database instance for storing and accessing data using Room
    private lateinit var database: AppDatabase

    // PostDao instance lazily initialized using the database
    private val postDao by lazy  {
        database.postDao()
    }

    /**
     * Called when the activity is created.
     * Initializes the database, sets the content view, and initializes UI components.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initializes the Room database instance
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()

        // Sets the content view using the MainScreen composable function
        setContent {
            MainScreen(applicationContext, viewModel, postDao, this@MainActivity)
        }
    }

    /**
     * Opens a WebView activity to display the provided URL.
     *
     * @param url The URL to be displayed in the WebView activity.
     */
    override fun openWebView(url: String) {
        val intent = Intent(this, WebviewActivity::class.java)
        intent.putExtra(URL_PARAMETER_REFERENCE, url)
        startActivity(intent)
    }
}