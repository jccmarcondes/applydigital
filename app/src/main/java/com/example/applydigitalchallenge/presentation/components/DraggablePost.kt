package com.example.applydigitalchallenge.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.applydigitalchallenge.presentation.callbacks.WebViewCallback
import com.example.applydigitalchallenge.presentation.model.Post
import com.example.applydigitalchallenge.util.ANIMATION_DURATION
import com.example.applydigitalchallenge.util.CARD_HEIGHT
import com.example.applydigitalchallenge.util.MIN_DRAG_AMOUNT
import com.example.applydigitalchallenge.util.diffInTimeFromNowToStandardTime
import java.util.*
import kotlin.math.roundToInt

/**
 * DraggablePost is a composable function that represents a draggable post card in a UI.
 * It displays a Card containing post information such as title, author, and time.
 * The card can be expanded or collapsed by dragging horizontally or by clicking on it.
 * Additionally, clicking on the card opens a web view to view the full post content.
 *
 * @param webViewCallback The callback interface for opening a web view to display the full post content.
 * @param post The Post object containing information about the post to be displayed.
 * @param isRevealed Indicates whether the post card is currently expanded or collapsed.
 * @param postOffset The horizontal offset of the post card when expanded or collapsed.
 * @param onExpand Callback function to be invoked when the post card is expanded.
 * @param onCollapse Callback function to be invoked when the post card is collapsed.
 */
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggablePost(
    webViewCallback: WebViewCallback,
    post: Post,
    isRevealed: Boolean,
    postOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {
    var showWebView by remember { mutableStateOf(false) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) -postOffset else 0f },

        )
    val cardElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 40.dp else 2.dp }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(CARD_HEIGHT.dp)
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount <= -MIN_DRAG_AMOUNT -> onExpand()
                        dragAmount > MIN_DRAG_AMOUNT -> onCollapse()
                    }
                }
            }
            .clickable { showWebView = true },
        shape = remember {
            RoundedCornerShape(0.dp)
        },
        elevation = cardElevation,
        content = {
            with(post) {
                if (title != null && author != null && createdAt != null) {
                    PostCard(
                        postTitle = title,
                        postAuthor = author,
                        postTime = diffInTimeFromNowToStandardTime(createdAt)
                    )
                }
            }
        }
    )

    if(showWebView) {
        post.url?.let {
            webViewCallback.openWebView(it)
        }
    }
}