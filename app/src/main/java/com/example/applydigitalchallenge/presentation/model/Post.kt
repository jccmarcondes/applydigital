package com.example.applydigitalchallenge.presentation.model

data class Post(
    val id: Long,
    val title: String?,
    val url: String?,
    val author: String?,
    val createdAt: String?
){
    companion object {
        fun mock() = Post(
            id = 12345,
            title = "Test",
            url = "https://www.test.com",
            author = "Julio Marcondes",
            createdAt = "Yesterday"
        )
    }
}