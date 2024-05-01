package com.example.applydigitalchallenge.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.applydigitalchallenge.util.ACTION_ROW_BUTTON_HEIGHT
import com.example.applydigitalchallenge.util.ACTION_ROW_BUTTON_WIDTH
import com.example.applydigitalchallenge.util.DELETE_ACTION_BUTTON_TEXT

/**
 * ActionRow is a composable function that represents a row containing an action button.
 * It displays a Button with the specified action and appearance.
 *
 * @param action The action to be performed when the button is clicked.
 */
@Composable
fun ActionRow(
    action: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .width(ACTION_ROW_BUTTON_WIDTH.dp)
                .height(ACTION_ROW_BUTTON_HEIGHT.dp)
                .align(Alignment.CenterEnd),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFB80F0A)
            ),
            onClick = { action() },
            content = {
                Text(
                    text = DELETE_ACTION_BUTTON_TEXT,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}