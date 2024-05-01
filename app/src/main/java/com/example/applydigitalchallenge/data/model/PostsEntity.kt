package com.example.applydigitalchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.applydigitalchallenge.util.APP_TABLE_NAME

@Entity(tableName = APP_TABLE_NAME)
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val postId: Long = 0,
    val title: String,
    val url: String?,
    val author: String?,
    val createdAt: String?
)