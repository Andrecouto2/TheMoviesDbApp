package br.com.andrecouto.nextel.themoviesdbapp.data.module

import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.DetailsMovieScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Module
import dagger.Provides

@Module
class DetailsMovieScreenModule(dMovieView: DetailsMovieScreenContract.dMovieView) {

    private val dMovieView: DetailsMovieScreenContract.dMovieView

    init {

        this.dMovieView = dMovieView
    }

    @Provides
    @CustomScope
    internal fun providesDetailsMovieContractView(): DetailsMovieScreenContract.dMovieView {
        return dMovieView
    }

}