package kumar.ritesh.newsapp.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import kumar.ritesh.newsapp.core.di.base.ViewModelFactory

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}