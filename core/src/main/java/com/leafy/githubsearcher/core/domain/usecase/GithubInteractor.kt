package com.leafy.githubsearcher.core.domain.usecase

import com.leafy.githubsearcher.core.data.Status
import com.leafy.githubsearcher.core.domain.model.Detail
import com.leafy.githubsearcher.core.domain.model.Repository
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.domain.repository.GithubDataSource
import kotlinx.coroutines.flow.Flow

class GithubInteractor(private val githubRepository: GithubDataSource) : GithubUseCase {
    override fun getSearchList(query: String): Flow<Status<List<User>>> =
        githubRepository.getSearchList(query)

    override fun getDetails(user: String): Flow<Status<Detail?>> = githubRepository.getDetails(user)

    override fun getFavoriteUser(user: String): Flow<User?> = githubRepository.getFavoriteUser(user)

    override fun getFavoriteList(): Flow<List<User>> = githubRepository.getFavoriteList()

    override fun setFavorite(user: User, state: Boolean) = githubRepository.setFavorite(user, state)

    override fun getRepositoryList(user: String): Flow<Status<List<Repository>>> =
        githubRepository.getRepositoryList(user)

    override fun getFollowerList(user: String): Flow<Status<List<User>>> =
        githubRepository.getFollowerList(user)

    override fun getFollowingList(user: String): Flow<Status<List<User>>> =
        githubRepository.getFollowingList(user)
}