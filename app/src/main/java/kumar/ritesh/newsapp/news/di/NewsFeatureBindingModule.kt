package kumar.ritesh.newsapp.news.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kumar.ritesh.newsapp.news.domain.NewsRepositoryModule
import kumar.ritesh.newsapp.news.ui.activity.NewsActivity

@Module(includes = [
    NewsViewModelModule::class,
    NewsRepositoryModule::class
])
interface NewsFeatureBindingModule {

    @ContributesAndroidInjector
    fun contributeNewsActivity(): NewsActivity
}