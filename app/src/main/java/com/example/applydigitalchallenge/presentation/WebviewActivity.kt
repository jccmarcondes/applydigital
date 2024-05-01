package com.example.applydigitalchallenge.presentation

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.applydigitalchallenge.presentation.screens.WebViewScreen
import com.example.applydigitalchallenge.util.URL_PARAMETER_REFERENCE
import dagger.hilt.android.AndroidEntryPoint

/**
 * WebviewActivity is an activity responsible for displaying a web page using a WebView component.
 * It extends AppCompatActivity and is annotated with @AndroidEntryPoint for Hilt dependency injection support.
 */
@AndroidEntryPoint
class WebviewActivity : AppCompatActivity() {
    /**
     * Called when the activity is created.
     * Initializes the WebView to load the URL passed via intent extras and sets up the UI.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieves the URL from the intent extras
        val url = intent.getStringExtra(URL_PARAMETER_REFERENCE)

        // Hides the action bar for fullscreen WebView experience
        supportActionBar?.hide()

        // Creates a new WebView instance
        val webView = WebView(this)

        // Loads the URL into the WebView if the URL is not null
        url?.let {
            webView.loadUrl(it)
        }

        // Sets the content view using the WebViewScreen composable function
        setContent {
            WebViewScreen(webView)
        }
    }
}