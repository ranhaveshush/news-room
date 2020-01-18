package com.example.baseapp.viewmodel

import androidx.lifecycle.*
import com.example.baseapp.repository.ArticlesRepository
import com.example.baseapp.vo.Article
import com.example.baseapp.vo.Resource
import kotlinx.coroutines.Dispatchers

class ArticleListViewModel(private val repository: ArticlesRepository) : ViewModel() {
    val sources = MutableLiveData<List<String>>()

    val articles = sources.switchMap { sources ->
        listArticles(sources)
    }

    private fun listArticles(sources: List<String>): LiveData<Resource<List<Article>>> = liveData {
        emitSource(repository.listArticles(sources).asLiveData(Dispatchers.IO))
    }
}
