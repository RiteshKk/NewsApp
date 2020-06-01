package kumar.ritesh.newsapp.news.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import kumar.ritesh.newsapp.core.di.NewsServiceModule
import kumar.ritesh.newsapp.core.di.ViewModelFactoryModule
import kumar.ritesh.newsapp.news.NewsApp
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            // Dagger support
            AndroidInjectionModule::class,

            // Global
            NewsDatabaseModule::class,
            NewsServiceModule::class,
            ViewModelFactoryModule::class,

            // News feature
            NewsFeatureBindingModule::class
        ]
)
interface AppComponent : AndroidInjector<NewsApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    override fun inject(newsApp: NewsApp)
}