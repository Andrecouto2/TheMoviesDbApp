package br.com.andrecouto.nextel.themoviesdbapp.data.component

import br.com.andrecouto.nextel.themoviesdbapp.ui.activity.MainActivity
import br.com.andrecouto.nextel.themoviesdbapp.data.module.MainScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.activity.DetailsMovieActivity
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Component

@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(MainScreenModule::class))
interface MainScreenComponent {
    fun inject(activity: MainActivity)
}