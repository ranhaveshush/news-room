package com.example.baseapp.api.news

import com.example.baseapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * News API [Interceptor] implementation,
 * adds the News API key name and value as a query parameter to every request.
 */
class NewsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()

        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(BuildConfig.API_KEY_NAME, BuildConfig.API_KEY_VALUE)
            .build()

        val newRequest = originalRequest.newBuilder().url(newHttpUrl).build()

        return chain.proceed(newRequest)
    }
}
