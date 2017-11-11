package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieWithCastGenreVideo

interface DetailsMovieScreenContract {

    interface dMovieView {
        fun onShowMovieWith(movieWith: MovieWithCastGenreVideo)
    }

    interface Presenter {
        fun getLocalDataMovieWith(moviedId: Int)
    }
}