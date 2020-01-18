package com.example.baseapp.api.news

import com.example.baseapp.api.ApiResponse
import com.example.baseapp.api.ArticlesResponse
import com.example.baseapp.api.ArticlesService
import java.io.IOException

/**
 * The [NewsApi] client.
 * Uses the given [NewsApi] instance to implement the [ArticlesService] contract.
 *
 * @param api The [NewsApi] implementation instance.
 */
class NewsClient(private val api: NewsApi) : ArticlesService {

    override suspend fun listArticles(sources: List<String>): ApiResponse<ArticlesResponse> {
        return try {
            val sourcesString = sources.toString().let {
                it.substring(1, it.length - 1)
            }
            val response = api.service.listArticles(sourcesString)
            ApiResponse.create(response)
        } catch (e: IOException) {
            ApiResponse.create(e)
        }
    }
}
