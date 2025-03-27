package com.example.assignnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignnews.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest

class NewsListFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)

        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        adapter = NewsAdapter { article ->
            val bundle = Bundle().apply {
                putString("article_title", article.title)
                putString("article_description", article.description)
                putString("article_published_at", article.publishedAt)
                putString("article_url", article.url)
            }
            findNavController().navigate(R.id.action_newsListFragment_to_newsDetailFragment, bundle)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.news.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        return view
    }
}