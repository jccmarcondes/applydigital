package com.example.applydigitalchallenge.presentation.components

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.applydigitalchallenge.util.BACK_ACTION_TOOLBAR_BUTTON_ICON_DESCRIPTION
import com.example.applydigitalchallenge.util.BACK_ACTION_TOOLBAR_BUTTON_TEXT

/**
 * CustomWebviewToolbar is a composable function that represents a custom toolbar for a WebView screen.
 * It displays a TopAppBar with a back navigation button and a title.
 *
 * The back navigation button allows the user to navigate back in the WebView history when clicked.
 */
@Composable
fun CustomWebviewToolbar() {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current

    TopAppBar(
        title = {
            Text(text = BACK_ACTION_TOOLBAR_BUTTON_TEXT)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressedDispatcher?.onBackPressedDispatcher?.onBackPressed()
                }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, BACK_ACTION_TOOLBAR_BUTTON_ICON_DESCRIPTION)
            }
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 4.dp
    )
}