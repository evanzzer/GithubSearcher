package com.leafy.githubsearcher.core.data.source.remote.network

import com.leafy.githubsearcher.core.BuildConfig

object ApiHeader {
    fun getHeaders(): HashMap<String, String> {
        val headers = HashMap<String, String>()
        headers["Authorization"] = BuildConfig.API_KEY
        headers["User-Agent"] = "request"
        return headers
    }
}