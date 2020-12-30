package com.leafy.githubsearcher.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leafy.githubsearcher.core.data.source.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        DetailEntity::class,
        RepositoryEntity::class,
        FollowerEntity::class,
        FollowingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao
}