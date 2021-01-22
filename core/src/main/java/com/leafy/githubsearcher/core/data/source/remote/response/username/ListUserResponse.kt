package com.leafy.githubsearcher.core.data.source.remote.response.username

import com.google.gson.annotations.SerializedName

data class ListUserResponse(
    @field:SerializedName("items")
    val items: List<UserResponse>
)
