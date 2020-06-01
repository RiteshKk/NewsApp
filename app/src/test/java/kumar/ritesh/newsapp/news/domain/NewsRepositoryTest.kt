package kumar.ritesh.newsapp.news.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kumar.ritesh.newsapp.BuildConfig
import kumar.ritesh.newsapp.core.ui.ViewState
import kumar.ritesh.newsapp.core.utils.MockitoTest
import kumar.ritesh.newsapp.core.utils.assertItems
import kumar.ritesh.newsapp.news.api.ApiService
import kumar.ritesh.newsapp.news.model.NewsArticles
import kumar.ritesh.newsapp.news.model.NewsSourceResponse
import kumar.ritesh.newsapp.news.storage.NewsArticlesDao
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import retrofit2.Response

@RunWith(JUnit4::class)
class NewsRepositoryTest : MockitoTest() {

    @Mock
    lateinit var newsDao: NewsArticlesDao

    @Mock
    lateinit var newsSourceService: ApiService

    @InjectMocks
    lateinit var newsRepository: DefaultNewsRepository

    @Test
    fun `get news articles when there is internet`() = runBlocking {
        // GIVEN
        val cachedArticles = listOf(NewsArticles(title = "Cached"))
        val fetchedArticles = listOf(NewsArticles(title = "Fetched"))
        val newsSource = NewsSourceResponse(articles = fetchedArticles)
        val response = Response.success(newsSource)

        // WHEN
        whenever(newsSourceService.getNewsFromApi(BuildConfig.API_KEY)) doReturn response
        whenever(newsDao.getNewsArticles()) doReturnConsecutively listOf(
            flowOf(cachedArticles),
            flowOf(fetchedArticles)
        )

        // THEN
        newsRepository.getNewsArticles().assertItems(
            ViewState.loading(),
            ViewState.success(cachedArticles),
            ViewState.success(fetchedArticles)
        )
    }

    @Test
    fun `get cached news articles when there is no internet`() = runBlocking {
        // GIVEN
        val cachedArticles = listOf(NewsArticles(title = "Cached"))
        val error = RuntimeException("Unable to fetch from network")

        // WHEN
        whenever(newsSourceService.getNewsFromApi(BuildConfig.API_KEY)) doThrow error
        whenever(newsDao.getNewsArticles()) doReturn flowOf(cachedArticles)

        // THEN
        newsRepository.getNewsArticles().assertItems(
            ViewState.loading(),
            ViewState.success(cachedArticles),
            ViewState.error(error.message.orEmpty())
        )
    }
}