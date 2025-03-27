package com.example.assignnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NewsDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_detail, container, false)

        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val publishedAtTextView = view.findViewById<TextView>(R.id.publishedAtTextView)
        val urlTextView = view.findViewById<TextView>(R.id.urlTextView)

        // Get article data from arguments
        arguments?.let { bundle ->
            titleTextView.text = bundle.getString("article_title", "No Title")
            descriptionTextView.text = bundle.getString("article_description", "No Description")
            publishedAtTextView.text = "Published: ${bundle.getString("article_published_at", "Unknown")}"
            urlTextView.text = bundle.getString("article_url", "No URL")
        }

        return view
    }
}