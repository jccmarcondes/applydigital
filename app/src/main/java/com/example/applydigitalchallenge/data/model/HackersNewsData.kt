package com.example.applydigitalchallenge.data.model

import com.google.gson.annotations.SerializedName

data class HackerNewsData (
    @SerializedName("hits")
    var hits: List<Hit>
)