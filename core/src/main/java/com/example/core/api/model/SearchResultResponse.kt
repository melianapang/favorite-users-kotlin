package com.example.core.api.model

import com.google.gson.annotations.SerializedName

data class SearchResultResponse (
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val isIncompleteResult: Boolean,
    @SerializedName("items")
    val items: ArrayList<UserResponse>,
)