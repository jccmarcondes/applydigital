package com.example.applydigitalchallenge.presentation.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.applydigitalchallenge.presentation.components.CustomWebviewToolbar

/**
 * WebViewScreen is a composable function that represents a screen displaying a WebView component.
 * It displays a Scaffold layout containing a custom toolbar and a WebView component.
 *
 * @param webView The WebView instance to be displayed on the screen.
 */
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WebViewScreen(webView: WebView) {
    Scaffold(
        topBar = {
            CustomWebviewToolbar()
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { webView }
            )
        }
    }
}