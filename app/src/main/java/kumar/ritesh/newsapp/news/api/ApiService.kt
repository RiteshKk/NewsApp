package kumar.ritesh.newsapp.news.api

import kumar.ritesh.newsapp.news.model.NewsSourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=in")
    suspend fun getNewsFromApi(@Query("apiKey") apiKey: String): Response<NewsSourceResponse>
}