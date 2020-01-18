package com.example.baseapp.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.baseapp.BuildConfig
import com.example.baseapp.R
import com.example.baseapp.databinding.FragmentArticleListBinding
import com.example.baseapp.ui.adapter.ArticleAdapter
import com.example.baseapp.ui.adapter.OnArticleItemClickListener
import com.example.baseapp.util.InjectorUtils
import com.example.baseapp.viewmodel.ArticleListViewModel
import com.example.baseapp.vo.Article
import com.example.baseapp.vo.Resource.Status

class ArticleListFragment : Fragment(R.layout.fragment_article_list), OnArticleItemClickListener {

    private val viewModel: ArticleListViewModel by viewModels {
        InjectorUtils.provideArticleListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArticleListBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        binding.recyclerViewArticles.adapter = ArticleAdapter(this@ArticleListFragment)

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            if (it.state.status == Status.SUCCESS) {
                (binding.recyclerViewArticles.adapter as ArticleAdapter).submitList(it.data)
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Should refresh the article list,
        // by the assignment requirement.
        viewModel.sources.value = getSourceList()
    }

    override fun onArticleClickListener(article: Article) {
        val articleUrl = Uri.parse(article.url)
        val toolbarColor = ActivityCompat.getColor(requireContext(), R.color.colorPrimary)

        val intent = CustomTabsIntent.Builder()
            .addDefaultShareMenuItem()
            .enableUrlBarHiding()
            .setToolbarColor(toolbarColor)
            .build()

        intent.launchUrl(context, articleUrl)
    }

    private fun getSourceList() = BuildConfig.SOURCES_VALUE.split(",").map { it }
}
