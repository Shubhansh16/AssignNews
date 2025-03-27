package com.example.assignnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.assignnews.NewsPagingSource
import com.example.assignnews.RetroInstance
import com.example.assignnews.model.Article
import kotlinx.coroutines.flow.Flow

class NewsViewModel:ViewModel() {
    private val apiKey = "35f43e2f984142a59f671d92adfceb24" // Replace with your NewsAPI key
    private val query = "technology"

    val news: Flow<PagingData<Article>> = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        )
    ){
        NewsPagingSource(RetroInstance.api, query, apiKey)
    }.flow.cachedIn(viewModelScope)
}