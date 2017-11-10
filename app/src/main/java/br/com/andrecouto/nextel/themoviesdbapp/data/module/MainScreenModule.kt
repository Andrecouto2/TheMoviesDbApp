package br.com.andrecouto.nextel.themoviesdbapp.data.module

import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.CustomScope
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule(dView: MainScreenContract.View,
                       pnMoviesView: MainScreenContract.PlayingNowMoviesView,
                       dMoviesView: MainScreenContract.DetailsMovieView,
                       vMoviesView: MainScreenContract.VideosMovieView,
                       cMoviesView: MainScreenContract.CastsMovieView) {
    private val dView: MainScreenContract.View
    internal var pnMoviesView: MainScreenContract.PlayingNowMoviesView
    internal var dMoviesView: MainScreenContract.DetailsMovieView
    internal var vMoviesView: MainScreenContract.VideosMovieView
    internal var cMoviesView: MainScreenContract.CastsMovieView

    init {
        this.dView = dView
        this.pnMoviesView = pnMoviesView
        this.dMoviesView = dMoviesView
        this.vMoviesView = vMoviesView
        this.cMoviesView = cMoviesView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractView(): MainScreenContract.View {
        return dView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractViewpnMovie(): MainScreenContract.PlayingNowMoviesView {
        return pnMoviesView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractViewdMovies(): MainScreenContract.DetailsMovieView {
        return dMoviesView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractViewvMovies(): MainScreenContract.VideosMovieView {
        return vMoviesView
    }

    @Provides
    @CustomScope
    internal fun providesMainScreenContractViewcMovies(): MainScreenContract.CastsMovieView{
        return cMoviesView
    }
}