package br.com.andrecouto.nextel.themoviesdbapp.data.module

import android.app.Application
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class AppModule(mApplication: Application) {

    internal var mApplication: Application

    init {
        this.mApplication = mApplication
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return mApplication
    }
}
