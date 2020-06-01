package kumar.ritesh.newsapp.news.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.row_news_article.view.*
import kumar.ritesh.newsapp.R
import kumar.ritesh.newsapp.databinding.RowNewsArticleBinding
import kumar.ritesh.newsapp.news.callback.ItemClickListener
import kumar.ritesh.newsapp.news.model.NewsArticles

class NewsArticlesAdapter(val listener: ItemClickListener
) : ListAdapter<NewsArticles, NewsArticlesAdapter.NewsHolder>(DIFF_CALLBACK) {

    /**
     * Inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsHolder(
        RowNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    /**
     * Bind the view with the data
     */
    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) = newsHolder.bind(getItem(position))

    /**
     * View Holder Pattern
     */
    inner class NewsHolder(var binding: RowNewsArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the UI with the data and handles clicks
         */
        fun bind(newsArticle: NewsArticles) = with(itemView) {
            binding.model = newsArticle
            newsImage.load(newsArticle.urlToImage) {
                placeholder(R.drawable.tools_placeholder)
                error(R.drawable.tools_placeholder)
            }
            setOnClickListener { listener.onItemClick(newsArticle.url ?: "") }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsArticles>() {
            override fun areItemsTheSame(oldItem: NewsArticles, newItem: NewsArticles): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: NewsArticles, newItem: NewsArticles): Boolean = oldItem == newItem
        }
    }
}