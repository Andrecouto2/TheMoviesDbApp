package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie

class SearchScreenContract {

    interface searchView {
        fun onShowMovieLile(movie: List<Movie>)
    }

    interface Presenter {
        fun getLocalDataMovieLike(query: String)
    }
}