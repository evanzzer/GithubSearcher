package com.leafy.githubsearcher.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "following",
        primaryKeys = ["username", "following"])
data class FollowingEntity (
    @NonNull
    @ColumnInfo(name = "username")
    var username: String,

    @NonNull
    @ColumnInfo(name = "following")
    var following: String,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String
)