package kumar.ritesh.newsapp.news.storage

import androidx.room.migration.Migration

internal object NewsDatabaseMigration {

    // Bump this on changing the schema
    const val latestVersion = 1

    val allMigrations: Array<Migration>
        get() = arrayOf()

    object V1 {

        object NewsArticles {
            const val tableName = "news_article"

            object Column {
                const val id = "id"
                const val author = "author"
                const val title = "title"
                const val description = "description"
                const val url = "url"
                const val urlToImage = "urlToImage"
                const val publishedAt = "publishedAt"
            }
        }
    }
}