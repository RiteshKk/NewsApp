package kumar.ritesh.newsapp.news.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kumar.ritesh.newsapp.core.di.base.ViewModelKey
import kumar.ritesh.newsapp.news.ui.viewmodel.NewsArticleViewModel

@Module
interface NewsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsArticleViewModel::class)
    fun bindNewsArticleViewModel(newsArticleViewModel: NewsArticleViewModel): ViewModel
}