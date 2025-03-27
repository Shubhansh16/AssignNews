package com.example.assignnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignnews.model.Article
import com.example.assignnews.service.NewsApiService
import retrofit2.HttpException
import java.io.IOException

class NewsPagingSource(
    private val api: NewsApiService,
    private val query: String,
    private val apiKey: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1 // Start at page 1 if no key provided
        return try {
            val response = api.getNews(query, apiKey, page, params.loadSize)
            val articles = response.articles
            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e) // Network error
        } catch (e: HttpException) {
            LoadResult.Error(e) // API error
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}