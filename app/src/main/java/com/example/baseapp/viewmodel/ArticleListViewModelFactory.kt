package com.example.baseapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseapp.repository.ArticlesRepository

class ArticleListViewModelFactory(
    private val repository: ArticlesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ArticleListViewModel(repository) as T
    }
}
