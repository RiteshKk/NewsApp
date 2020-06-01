package kumar.ritesh.newsapp.news.ui.activity

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_layout.*
import kotlinx.android.synthetic.main.progress_layout.*
import kumar.ritesh.newsapp.R
import kumar.ritesh.newsapp.core.ui.ViewState
import kumar.ritesh.newsapp.core.ui.base.BaseActivity
import kumar.ritesh.newsapp.core.utils.getViewModel
import kumar.ritesh.newsapp.core.utils.observeNotNull
import kumar.ritesh.newsapp.core.utils.toast
import kumar.ritesh.newsapp.news.callback.ItemClickListener
import kumar.ritesh.newsapp.news.ui.adapter.NewsArticlesAdapter
import kumar.ritesh.newsapp.news.ui.viewmodel.NewsArticleViewModel

class NewsActivity : BaseActivity(), ItemClickListener {

    private val newsArticleViewModel by lazy { getViewModel<NewsArticleViewModel>() }

    /**
     * Starting point of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting up RecyclerView and adapter
        newsList.setEmptyView(empty_view)
        newsList.setProgressView(progress_view)

        val adapter = NewsArticlesAdapter(this)
        newsList.adapter = adapter

        // Update the UI on state change
        newsArticleViewModel.getNewsArticles().observeNotNull(this) { state ->
            when (state) {
                is ViewState.Success -> adapter.submitList(state.data)
                is ViewState.Loading -> newsList.showLoading()
                is ViewState.Error -> toast("Something went wrong => ${state.message}")
            }
        }

    }

    override fun onItemClick(url: String) {
        val webViewActivity = Intent(this, WebViewActivity::class.java)
        webViewActivity.putExtra("url", url)
        startActivity(webViewActivity)
    }
}
