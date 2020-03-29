/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.baseapp.api

import retrofit2.Response

/**
 * This class decouples the used networking library (e.g. Retrofit) from the rest of the app.
 *
 * Used by API clients to transform specific API response
 * (e.g. Retrofit [Response][retrofit2.Response]) to [ApiResponse].
 *
 * Used by repositories to transform the [API response][ApiResponse] to a [resource][Resource].
 *
 * @param T The type of the response data object
 */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        private const val UNKNOWN_ERROR = "unknown error"

        /**
         * Creates an [ErrorApiResponse] from a given [error][Throwable].
         */
        fun <T> create(error: Throwable): ErrorApiResponse<T> {
            return ErrorApiResponse(error.message ?: UNKNOWN_ERROR, error)
        }

        /**
         * Creates an [ApiResponse] from a given Retrofit [Response][retrofit2.Response].
         */
        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    EmptyApiResponse()
                } else {
                    SuccessApiResponse(body)
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                val message = if (errorMessage.isNullOrEmpty()) {
                    response.message()
                } else {
                    errorMessage
                }
                ErrorApiResponse(message ?: UNKNOWN_ERROR)
            }
        }
    }
}

/**
 * An empty success response.
 */
class EmptyApiResponse<T> : ApiResponse<T>()

/**
 * A success response with a typed [T] data as it's body.
 */
data class SuccessApiResponse<T>(val body: T) : ApiResponse<T>()

/**
 * An error response with a message and an optional [cause][Throwable].
 */
data class ErrorApiResponse<T>(
    val message: String,
    val error: Throwable? = null
) : ApiResponse<T>()
