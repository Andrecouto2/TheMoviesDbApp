package br.com.andrecouto.nextel.themoviesdbapp.data.component

import br.com.andrecouto.nextel.themoviesdbapp.data.module.*;
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface NetComponent {
    fun retrofit(): Retrofit
}