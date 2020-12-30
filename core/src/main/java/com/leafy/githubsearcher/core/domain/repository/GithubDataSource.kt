package com.leafy.githubsearcher.core.domain.repository

import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface GithubDataSource {
    fun getSearchList(query: String): Flow<Status<List<User>>>

    fun getDetails(user: String): Flow<Status<Detail?>>

    fun getFavoriteUser(user: String): Flow<User?>

    fun getFavoriteList(): Flow<List<User>>

    fun setFavorite(user: User, state: Boolean)

    fun getRepositoryList(user: String): Flow<Status<List<Repository>>>

    fun getFollowerList(user: String): Flow<Status<List<User>>>

    fun getFollowingList(user: String): Flow<Status<List<User>>>
}