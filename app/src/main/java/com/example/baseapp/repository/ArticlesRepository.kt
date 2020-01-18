package com.example.baseapp.repository

import com.example.baseapp.api.ApiResponse
import com.example.baseapp.api.ArticlesResponse
import com.example.baseapp.api.ArticlesService
import com.example.baseapp.api.SuccessApiResponse
import com.example.baseapp.db.ArticlesDao
import com.example.baseapp.vo.Article

class ArticlesRepository(
    private val articlesDao: ArticlesDao,
    private val articlesService: ArticlesService
) {
    suspend fun listArticles(sources: List<String>) =
        object : NetworkBoundResource<List<Article>, ArticlesResponse>() {
            override suspend fun loadFromDB(): List<Article> = articlesDao.getAll(sources)

            override fun isNotEmpty(data: List<Article>?): Boolean = data?.isNotEmpty() ?: false

            // Should fetch always, by the assignment requirement.
            override fun shouldFetch(data: List<Article>?): Boolean = true

            override suspend fun createCall(): ApiResponse<ArticlesResponse> =
                articlesService.listArticles(sources)

            override suspend fun saveCallResult(data: List<Article>) {
                articlesDao.insertAll(data)
            }

            override fun processResponse(response: SuccessApiResponse<ArticlesResponse>): List<Article> {
                return response.body.articles
            }
        }.asFlow()
}
