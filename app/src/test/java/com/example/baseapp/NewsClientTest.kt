package com.example.baseapp

import com.example.baseapp.api.EmptyApiResponse
import com.example.baseapp.api.ErrorApiResponse
import com.example.baseapp.api.SuccessApiResponse
import com.example.baseapp.api.news.NewsApi
import com.example.baseapp.api.news.NewsClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.junit.Test

class NewsClientTest {

    private val client = NewsClient(NewsApi)

    @Test
    fun should_listArticles_when_validSource() {
        val sources = listOf("the-verge")

        val apiResponse = runBlocking(Dispatchers.IO) {
            client.listArticles(sources)
        }

        when (apiResponse) {
            is SuccessApiResponse -> {
                val articles = apiResponse.body.articles
                assert(articles.isNotEmpty())
            }
            is EmptyApiResponse -> {
                fail("None articles were found.")
            }
            is ErrorApiResponse -> {
                fail(apiResponse.message)
            }
        }
    }

    @Test
    fun should_listArticles_when_validSources() {
        val sources = listOf("the-verge", "bbc-news")

        val apiResponse = runBlocking(Dispatchers.IO) {
            client.listArticles(sources)
        }

        when (apiResponse) {
            is SuccessApiResponse -> {
                val articles = apiResponse.body.articles
                assert(articles.isNotEmpty())
            }
            is EmptyApiResponse -> {
                fail("None articles were found.")
            }
            is ErrorApiResponse -> {
                fail(apiResponse.message)
            }
        }
    }

    @Test
    fun shouldNot_listArticles_when_invalidSources() {
        val sources = listOf("the-vergee")

        val apiResponse = runBlocking(Dispatchers.IO) {
            client.listArticles(sources)
        }

        when (apiResponse) {
            is SuccessApiResponse -> {
                val articles = apiResponse.body.articles
                assert(articles.isEmpty())
            }
            is EmptyApiResponse -> {
                fail("None articles were found.")
            }
            is ErrorApiResponse -> {
                fail(apiResponse.message)
            }
        }
    }
}
