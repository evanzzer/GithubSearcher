package com.leafy.githubsearcher.core.data.source.remote.response.repo

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("name")
    val name: String
)
