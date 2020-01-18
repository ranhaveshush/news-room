package com.example.baseapp.api

/**
 * The articles service contract to the rest of the app.
 */
interface ArticlesService {
    /**
     * Lists top headlines articles from the given news provider sources.
     *
     * @param sources A comma separated string of the news provider sources.
     */
    suspend fun listArticles(sources: List<String>): ApiResponse<ArticlesResponse>
}
