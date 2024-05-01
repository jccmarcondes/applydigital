package com.example.applydigitalchallenge.data.model

import com.google.gson.annotations.SerializedName

data class Hit (
    @SerializedName("title")
    var title: String?,

    @SerializedName("url")
    var url: String?,

    @SerializedName("story_id")
    var storyId: Long?,

    @SerializedName("story_title")
    var storyTitle: String?,

    @SerializedName("story_url")
    var storyUrl: String?,

    @SerializedName("author")
    var author: String,

    @SerializedName("created_at")
    var createdAt: String?,

    @SerializedName("updated_at")
    var updatedAt: String?
)
