package com.leafy.githubsearcher.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "follower",
        primaryKeys = ["username", "follower"])
data class FollowerEntity(
    @NonNull
    @ColumnInfo(name = "username")
    var username: String,

    @NonNull
    @ColumnInfo(name = "follower")
    var follower: String,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String
)
