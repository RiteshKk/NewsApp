package kumar.ritesh.newsapp.news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kumar.ritesh.newsapp.core.ui.ViewState
import kumar.ritesh.newsapp.news.domain.NewsRepository
import kumar.ritesh.newsapp.news.model.NewsArticles
import javax.inject.Inject

class NewsArticleViewModel @Inject constructor(
        newsRepository: NewsRepository
) : ViewModel() {

    private val newsArticles: LiveData<ViewState<List<NewsArticles>>> = newsRepository.getNewsArticles().asLiveData()

    /**
     * Return news articles to observeNotNull on the UI.
     */
    fun getNewsArticles(): LiveData<ViewState<List<NewsArticles>>> = newsArticles
}