package br.com.andrecouto.nextel.themoviesdbapp.data.module

import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.DetailsMovieScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.SearchScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Module
import dagger.Provides

@Module
class SearchScreenModule(searchView : SearchScreenContract.searchView) {

    private val searchView: SearchScreenContract.searchView

    init {

        this.searchView = searchView
    }

    @Provides
    @CustomScope
    internal fun providesDetailsMovieContractView(): SearchScreenContract.searchView {
        return searchView
    }

}