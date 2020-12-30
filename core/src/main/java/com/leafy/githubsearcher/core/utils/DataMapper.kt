package com.leafy.githubsearcher.core.utils

import com.leafy.githubsearcher.core.data.source.local.entity.*
import com.leafy.githubsearcher.core.data.source.remote.response.detail.DetailResponse
import com.leafy.githubsearcher.core.data.source.remote.response.repo.RepositoryResponse
import com.leafy.githubsearcher.core.data.source.remote.response.username.UserResponse
import com.leafy.githubsearcher.core.domain.model.Detail
import com.leafy.githubsearcher.core.domain.model.Repository
import com.leafy.githubsearcher.core.domain.model.User

object DataMapper {

    fun mapUserResponseToDomain(input: List<UserResponse>): List<User> {
        val userList = ArrayList<User>()
        input.map { response ->
            userList.add(User(
                    username = response.login,
                    avatarUrl = response.avatarUrl,
                    isFavorite = false
            ))
        }
        return userList
    }

    fun mapUserEntityToDomain(input: List<UserEntity>): List<User> {
        val userList = ArrayList<User>()
        input.map { entity ->
            userList.add(User(
                    username = entity.username,
                    avatarUrl = entity.avatarUrl,
                    isFavorite = entity.isFavorite
            ))
        }
        return userList
    }

    fun mapFavoriteEntityToDomain(input: UserEntity): User = User(
        username = input.username,
        avatarUrl = input.avatarUrl,
        isFavorite = input.isFavorite
    )

    fun mapUserDomainToEntity(input: User): UserEntity = UserEntity(
            username = input.username,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
    )

    fun mapDetailResponseToEntity(input: DetailResponse): DetailEntity = DetailEntity(
            username = input.login,
            avatarUrl = input.avatarUrl,
            githubUrl = input.htmlUrl,
            name = input.name,
            company = input.company,
            location = input.location,
            email = input.email,
            repository = input.publicRepos,
            follower = input.followers,
            following = input.following
    )

    fun mapDetailEntityToDomain(input: DetailEntity): Detail {
        val nullText = "Not available"
        return Detail(
            username = input.username,
            avatarUrl = input.avatarUrl,
            githubUrl = input.githubUrl,
            name = input.name ?: nullText,
            company = input.company ?: nullText,
            location = input.location ?: nullText,
            email = input.email ?: nullText,
            repository = input.repository,
            follower = input.follower,
            following = input.following
        )
    }

    fun mapRepositoryResponseToEntity(input: List<RepositoryResponse>, user: String): List<RepositoryEntity> {
        val repositoryList = ArrayList<RepositoryEntity>()
        input.map { response ->
            repositoryList.add(RepositoryEntity(
                    owner = user,
                    name = response.name,
                    repoUrl = response.htmlUrl
            ))
        }
        return repositoryList
    }

    fun mapRepositoryEntityToDomain(input: List<RepositoryEntity>): List<Repository> {
        val repositoryList = ArrayList<Repository>()
        input.map { entity ->
            repositoryList.add(Repository(
                    name = entity.name,
                    url = entity.repoUrl
            ))
        }
        return repositoryList
    }

    fun mapFollowerResponseToEntity(input: List<UserResponse>, user: String): List<FollowerEntity> {
        val followerList = ArrayList<FollowerEntity>()
        input.map { response ->
            followerList.add(FollowerEntity(
                    username = user,
                    follower = response.login,
                    avatarUrl = response.avatarUrl
            ))
        }
        return followerList
    }

    fun mapFollowerEntityToDomain(input: List<FollowerEntity>): List<User> {
        val followerList = ArrayList<User>()
        input.map { entity ->
            followerList.add(User(
                    username = entity.follower,
                    avatarUrl = entity.avatarUrl
            ))
        }
        return followerList
    }

    fun mapFollowingResponseToEntity(input: List<UserResponse>, user: String): List<FollowingEntity> {
        val followingList = ArrayList<FollowingEntity>()
        input.map { response ->
            followingList.add(FollowingEntity(
                    username = user,
                    following = response.login,
                    avatarUrl = response.avatarUrl
            ))
        }
        return followingList
    }

    fun mapFollowingEntityToDomain(input: List<FollowingEntity>): List<User> {
        val followingList = ArrayList<User>()
        input.map { entity ->
            followingList.add(User(
                    username = entity.following,
                    avatarUrl = entity.avatarUrl
            ))
        }
        return followingList
    }
}