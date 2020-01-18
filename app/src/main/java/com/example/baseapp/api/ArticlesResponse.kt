package com.example.baseapp.api

import com.example.baseapp.vo.Article

/**
 * Common class for articles responses.
 *
 * @param status String represents the response status.
 * @param totalResults The number of articles returned in the response.
 * @param articles The list of [Article]s.
 */
data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
