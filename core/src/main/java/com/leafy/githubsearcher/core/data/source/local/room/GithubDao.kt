package com.leafy.githubsearcher.core.data.source.local.room

import androidx.room.*
import com.leafy.githubsearcher.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao {
    // Details
    @Query("SELECT * from detail where username = :username LIMIT 1")
    fun getDetails(username: String): Flow<DetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insertDetails(detail: DetailEntity)

    // Favorite
    @Query("SELECT * from favorite where username = :username LIMIT 1")
    fun getFavorite(username: String): Flow<UserEntity?>

    @Query("SELECT * from favorite where isFavorite = 1")
    fun getFavoriteList(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    // Repository
    @Query("SELECT * from repository where owner = :username")
    fun getRepositoryList(username: String): Flow<List<RepositoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryEntity>)

    // Followers
    @Query("SELECT * from follower where username = :username")
    fun getFollowerList(username: String): Flow<List<FollowerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowers(followers: List<FollowerEntity>)

    // Following
    @Query("SELECT * from following where username = :username")
    fun getFollowingList(username: String): Flow<List<FollowingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollowings(followings: List<FollowingEntity>)
}