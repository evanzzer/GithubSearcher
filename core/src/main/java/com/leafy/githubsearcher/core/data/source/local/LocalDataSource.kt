package com.leafy.githubsearcher.core.data.source.local

import com.leafy.githubsearcher.core.data.source.local.entity.*
import com.leafy.githubsearcher.core.data.source.local.room.GithubDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val githubDao: GithubDao){
    fun getDetails(user: String) = githubDao.getDetails(user)

    suspend fun insertDetails(detail: DetailEntity) = githubDao.insertDetails(detail)

    fun getFavoriteUser(user: String) = githubDao.getFavorite(user)

    fun getFavoriteList(): Flow<List<UserEntity>> = githubDao.getFavoriteList()

    fun insertFavoriteUser(user: UserEntity) = githubDao.insertUser(user)

    fun getRepositoryList(user: String): Flow<List<RepositoryEntity>> = githubDao.getRepositoryList(user)

    suspend fun insertRepositories(repositories: List<RepositoryEntity>) = githubDao.insertRepositories(repositories)

    fun getFollowerList(user: String): Flow<List<FollowerEntity>> = githubDao.getFollowerList(user)

    suspend fun insertFollowers(followers: List<FollowerEntity>) = githubDao.insertFollowers(followers)

    fun getFollowingList(user: String): Flow<List<FollowingEntity>> = githubDao.getFollowingList(user)

    suspend fun insertFollowings(followings: List<FollowingEntity>) = githubDao.insertFollowings(followings)
}