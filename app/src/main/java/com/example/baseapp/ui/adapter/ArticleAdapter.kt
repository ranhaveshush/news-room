package com.example.baseapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseapp.databinding.ListItemArticleBinding
import com.example.baseapp.vo.Article

class ArticleAdapter(
    private val listener: OnArticleItemClickListener
) : ListAdapter<Article, ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ListItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ArticleViewHolder(
    private val binding: ListItemArticleBinding,
    private val listener: OnArticleItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clickListener = View.OnClickListener {
            binding.article?.let {
                listener.onArticleClickListener(it)
            }
        }
    }

    fun bind(item: Article) {
        binding.apply {
            article = item
            executePendingBindings()
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article) =
        oldItem.author == newItem.author && oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
}

interface OnArticleItemClickListener {
    fun onArticleClickListener(article: Article)
}
