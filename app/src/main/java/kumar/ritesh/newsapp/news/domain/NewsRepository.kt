package kumar.ritesh.newsapp.news.domain

import dagger.Binds
import dagger.Module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kumar.ritesh.newsapp.BuildConfig
import kumar.ritesh.newsapp.core.domain.NetworkBoundResource
import kumar.ritesh.newsapp.core.ui.ViewState
import kumar.ritesh.newsapp.news.api.ApiService
import kumar.ritesh.newsapp.news.model.NewsArticles
import kumar.ritesh.newsapp.news.model.NewsSourceResponse
import kumar.ritesh.newsapp.news.storage.NewsArticlesDao
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface NewsRepository {

    /**
     * Gets the cached news article from database and tries to get
     * fresh news articles from web and save into database
     * if that fails then continues showing cached data.
     */
    fun getNewsArticles(): Flow<ViewState<List<NewsArticles>>>
}

@Singleton
class DefaultNewsRepository @Inject constructor(
    private val newsDao: NewsArticlesDao,
    private val newsService: ApiService
) : NewsRepository {

    override fun getNewsArticles(): Flow<ViewState<List<NewsArticles>>> {
        return object : NetworkBoundResource<List<NewsArticles>, NewsSourceResponse>() {
            override suspend fun saveNetworkResult(response: NewsSourceResponse) = newsDao.clearAndCacheArticles(response.articles)
            // Always try to fetch new articles
            override fun shouldFetch(data: List<NewsArticles>?): Boolean = true
            override fun loadFromDb(): Flow<List<NewsArticles>> = newsDao.getNewsArticles()
            override suspend fun fetchFromNetwork(): Response<NewsSourceResponse> = newsService.getNewsFromApi(
                BuildConfig.API_KEY)
        }
        .asFlow()
        .flowOn(Dispatchers.IO)
    }
}

@Module
interface NewsRepositoryModule {
    /* Exposes the concrete implementation for the interface */
    @Binds
    fun it(it: DefaultNewsRepository): NewsRepository
}