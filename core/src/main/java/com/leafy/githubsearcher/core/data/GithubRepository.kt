package com.leafy.githubsearcher.core.data

import com.leafy.githubsearcher.core.data.source.local.LocalDataSource
import com.leafy.githubsearcher.core.data.source.remote.RemoteDataSource
import com.leafy.githubsearcher.core.data.source.remote.network.ApiResponse
import com.leafy.githubsearcher.core.data.source.remote.response.detail.DetailResponse
import com.leafy.githubsearcher.core.data.source.remote.response.repo.RepositoryResponse
import com.leafy.githubsearcher.core.data.source.remote.response.username.UserResponse
import com.leafy.githubsearcher.core.domain.model.Detail
import com.leafy.githubsearcher.core.domain.model.Repository
import com.leafy.githubsearcher.core.domain.model.User
import com.leafy.githubsearcher.core.domain.repository.GithubDataSource
import com.leafy.githubsearcher.core.utils.AppExecutors
import com.leafy.githubsearcher.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class GithubRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : GithubDataSource {
    override fun getSearchList(query: String): Flow<Status<List<User>>> =
        flow {
            emit(Status.Loading())

            val response = remoteDataSource.getSearchList(query)
            when (val apiResponse = response.first()) {
                is ApiResponse.Success -> {
                    val userList = DataMapper.mapUserResponseToDomain(apiResponse.data)
                    emit(Status.Success(userList))
                }
                is ApiResponse.Empty -> emit(Status.Empty<List<User>>())
                is ApiResponse.Error -> emit(Status.Error<List<User>>(apiResponse.errorMessage))
            }
        }

    override fun getDetails(user: String): Flow<Status<Detail?>> =
        object : NetworkBoundResource<Detail?, DetailResponse>() {
            override fun loadFromDB(): Flow<Detail?> =
                localDataSource.getDetails(user).map { DataMapper.mapDetailEntityToDomain(it) }

            override fun shouldFetch(data: Detail?): Boolean = data == null

            override suspend fun createCall(): Flow<ApiResponse<DetailResponse>> =
                remoteDataSource.getDetails(user)

            override suspend fun saveCallResult(data: DetailResponse) =
                localDataSource.insertDetails(DataMapper.mapDetailResponseToEntity(data))
        }.asFlow()

    override fun getFavoriteUser(user: String): Flow<User?> =
        localDataSource.getFavoriteUser(user).map { DataMapper.mapFavoriteEntityToDomain(it) }

    override fun getFavoriteList(): Flow<List<User>> =
        localDataSource.getFavoriteList().map { DataMapper.mapUserEntityToDomain(it) }

    override fun setFavorite(user: User, state: Boolean) {
        val userEntity = DataMapper.mapUserDomainToEntity(user)
        userEntity.isFavorite = state
        appExecutors.diskIO().execute { localDataSource.insertFavoriteUser(userEntity) }
    }

    override fun getRepositoryList(user: String): Flow<Status<List<Repository>>> =
        object : NetworkBoundResource<List<Repository>, List<RepositoryResponse>>() {
            override fun loadFromDB(): Flow<List<Repository>> =
                localDataSource.getRepositoryList(user)
                    .map { DataMapper.mapRepositoryEntityToDomain(it) }

            override fun shouldFetch(data: List<Repository>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<RepositoryResponse>>> =
                remoteDataSource.getRepositoryList(user)

            override suspend fun saveCallResult(data: List<RepositoryResponse>) =
                localDataSource.insertRepositories(
                    DataMapper.mapRepositoryResponseToEntity(
                        data,
                        user
                    )
                )

        }.asFlow()


    override fun getFollowerList(user: String): Flow<Status<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun loadFromDB(): Flow<List<User>> =
                localDataSource.getFollowerList(user)
                    .map { DataMapper.mapFollowerEntityToDomain(it) }

            override fun shouldFetch(data: List<User>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowerList(user)

            override suspend fun saveCallResult(data: List<UserResponse>) =
                localDataSource.insertFollowers(DataMapper.mapFollowerResponseToEntity(data, user))

        }.asFlow()

    override fun getFollowingList(user: String): Flow<Status<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>() {
            override fun loadFromDB(): Flow<List<User>> =
                localDataSource.getFollowingList(user)
                    .map { DataMapper.mapFollowingEntityToDomain(it) }

            override fun shouldFetch(data: List<User>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowingList(user)

            override suspend fun saveCallResult(data: List<UserResponse>) =
                localDataSource.insertFollowings(
                    DataMapper.mapFollowingResponseToEntity(
                        data,
                        user
                    )
                )

        }.asFlow()
}