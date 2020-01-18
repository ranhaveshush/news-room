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
 * Common class used by API responses.
 *
 * @param T The type of the response object
 */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ErrorApiResponse<T> {
            return ErrorApiResponse(error.message ?: "unknown error", error)
        }

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
                ErrorApiResponse(message ?: "unknown error")
            }
        }
    }
}

class EmptyApiResponse<T> : ApiResponse<T>()

data class SuccessApiResponse<T>(val body: T) : ApiResponse<T>()

data class ErrorApiResponse<T>(
    val message: String,
    val error: Throwable? = null
) : ApiResponse<T>()
