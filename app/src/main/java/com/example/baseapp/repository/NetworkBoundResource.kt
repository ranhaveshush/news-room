package com.example.baseapp.repository

import com.example.baseapp.api.ApiResponse
import com.example.baseapp.api.ErrorApiResponse
import com.example.baseapp.api.SuccessApiResponse
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @param ResultType Type for the [Resource] data.
 * @param RequestType Type for the API response.
 */
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.loading())

        val data = loadFromDB()

        if (isNotEmpty(data)) {
            emit(Resource.success(data))
        }

        if (shouldFetch(data)) {
            when (val response: ApiResponse<RequestType> = createCall()) {
                is SuccessApiResponse -> {
                    val processedResponse = processResponse(response)
                    saveCallResult(processedResponse)
                    val newData = loadFromDB()
                    emit(Resource.success(newData))
                }
                is ErrorApiResponse -> {
                    onFetchFailed()
                    emit(Resource.error(response.message))
                }
            }
        }
    }

    fun asFlow() = result

    protected abstract suspend fun loadFromDB(): ResultType

    protected abstract fun isNotEmpty(data: ResultType?): Boolean

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): ApiResponse<RequestType>

    protected abstract suspend fun saveCallResult(data: ResultType)

    protected abstract fun processResponse(response: SuccessApiResponse<RequestType>): ResultType

    protected open fun onFetchFailed() {}
}
