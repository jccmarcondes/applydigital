package com.example.applydigitalchallenge.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * PostCard is a composable function that represents a card displaying information about a post.
 * It consists of a title, author, and time of the post.
 *
 * @param postTitle The title of the post to be displayed.
 * @param postAuthor The author of the post to be displayed.
 * @param postTime The time when the post was published or updated.
 */
@Composable
fun PostCard(
    postTitle: String,
    postAuthor: String,
    postTime: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = postTitle,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$postAuthor - $postTime",
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            textAlign = TextAlign.Left
        )
    }
}