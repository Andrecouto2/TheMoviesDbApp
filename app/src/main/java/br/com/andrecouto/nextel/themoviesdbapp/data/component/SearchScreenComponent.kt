package br.com.andrecouto.nextel.themoviesdbapp.data.component

import br.com.andrecouto.nextel.themoviesdbapp.data.module.SearchScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.activity.SearchResultsActivity
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Component

@CustomScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(SearchScreenModule::class))
interface  SearchScreenComponent {
    fun inject(activity: SearchResultsActivity)
}