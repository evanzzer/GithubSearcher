package com.leafy.githubsearcher.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String,
    var avatarUrl: String,
    var isFavorite: Boolean = false
) : Parcelable
