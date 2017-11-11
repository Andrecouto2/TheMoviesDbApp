package br.com.andrecouto.nextel.themoviesdbapp.data.component

import br.com.andrecouto.nextel.themoviesdbapp.data.module.DetailsMovieScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.activity.DetailsMovieActivity
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Component

@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(DetailsMovieScreenModule::class))
interface DetailsMovieScreenComponent {
    fun inject(activity: DetailsMovieActivity)
}