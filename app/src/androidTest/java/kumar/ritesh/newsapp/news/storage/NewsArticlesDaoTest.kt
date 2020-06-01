package kumar.ritesh.newsapp.news.storage

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import kumar.ritesh.newsapp.news.model.NewsArticles
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsArticlesDaoTest {

    private lateinit var db: NewsDatabase

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), NewsDatabase::class.java).build()
    }

    @After
    fun closeDb() = db.close()

    @Test
    @Throws(InterruptedException::class)
    fun insertNewsArticles() {
        // GIVEN
        val input = listOf(NewsArticles(1), NewsArticles(2))

        // THEN
        assertThat(db.newsArticlesDao().insertArticles(input), equalTo(listOf(1L, 2L)))
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertNewsArticlesAndRead(): Unit = runBlocking {
        // GIVEN
        val input = listOf(
                NewsArticles(1, "First", "Hello"),
                NewsArticles(2, "Second", "Testing")
        )
        db.newsArticlesDao().insertArticles(input)

        // THEN
        val articles = db.newsArticlesDao().getNewsArticles()
        assertThat(articles.size, equalTo(articles.size))
        assertThat(articles, equalTo(articles))
    }

}