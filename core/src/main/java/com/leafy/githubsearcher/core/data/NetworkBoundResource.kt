package com.leafy.githubsearcher.core.data

import com.leafy.githubsearcher.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Status<ResultType>> = flow {
        emit(Status.Loading())

        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Status.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Status.Success(it) })
                }
                is ApiResponse.Empty -> emit(Status.Empty<ResultType>())
                is ApiResponse.Error -> emit(Status.Error<ResultType>(apiResponse.errorMessage))
            }
        } else emitAll(loadFromDB().map { Status.Success(it) })
    }

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Status<ResultType>> = result
}