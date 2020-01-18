package com.example.baseapp.util

import android.content.Context
import com.example.baseapp.api.news.NewsApi
import com.example.baseapp.api.news.NewsClient
import com.example.baseapp.db.NewsDatabase
import com.example.baseapp.repository.ArticlesRepository
import com.example.baseapp.viewmodel.ArticleListViewModelFactory

object InjectorUtils {
    fun provideArticleListViewModelFactory(context: Context): ArticleListViewModelFactory {
        return ArticleListViewModelFactory(getArticlesRepository(context))
    }

    private fun getArticlesRepository(context: Context) = ArticlesRepository(
        getArticlesDao(context),
        getNewsClient()
    )

    private fun getArticlesDao(context: Context) = getNewsDatabase(context).getArticlesDao()

    private fun getNewsDatabase(context: Context) = NewsDatabase.getInstance(context)

    private fun getNewsClient() = NewsClient(NewsApi)
}
