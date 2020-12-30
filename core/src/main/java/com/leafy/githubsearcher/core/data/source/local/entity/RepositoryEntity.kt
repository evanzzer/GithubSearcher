package com.leafy.githubsearcher.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "repository",
        primaryKeys = ["owner", "name"])
data class RepositoryEntity(
    @NonNull
    @ColumnInfo(name = "owner")
    var owner: String,

    @NonNull
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "repoUrl")
    var repoUrl: String,
)
