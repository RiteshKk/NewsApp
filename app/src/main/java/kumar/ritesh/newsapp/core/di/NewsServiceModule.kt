package kumar.ritesh.newsapp.core.di

import dagger.Module
import dagger.Provides
import kumar.ritesh.newsapp.BuildConfig
import kumar.ritesh.newsapp.news.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NewsServiceModule {

    @Singleton
    @Provides
    fun provideNewsService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}