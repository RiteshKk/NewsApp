package kumar.ritesh.newsapp.news.di

import android.app.Application
import dagger.Module
import dagger.Provides
import kumar.ritesh.newsapp.news.storage.NewsArticlesDao
import kumar.ritesh.newsapp.news.storage.NewsDatabase
import javax.inject.Singleton

@Module
object NewsDatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): NewsDatabase = NewsDatabase.buildDefault(app)

    @Singleton
    @Provides
    fun provideUserDao(db: NewsDatabase): NewsArticlesDao = db.newsArticlesDao()
}