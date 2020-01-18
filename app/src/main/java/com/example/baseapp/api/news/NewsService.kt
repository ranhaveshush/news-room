package com.example.baseapp.api.news

import com.example.baseapp.BuildConfig
import com.example.baseapp.api.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The [News API](https://newsapi.org/docs/get-started) web service.
 */
interface NewsService {
    /**
     * Lists top headlines articles from the given news provider sources.
     * Using the [News API top headlines](https://newsapi.org/docs/endpoints/top-headlines) endpoint.
     *
     * @param sources - A comma separated string of the news provider sources.
     * (e.g. 'the-verge,engadget,techcrunch')
     */
    @GET("top-headlines")
    suspend fun listArticles(@Query(BuildConfig.SOURCES_NAME) sources: String): Response<ArticlesResponse>
}
