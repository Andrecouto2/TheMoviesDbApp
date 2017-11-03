package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie

interface MainScreenContract {
    interface View {
        fun showPosts(posts: List<Movie>?)
        fun showError(message: String)
        fun showComplete()
    }

    interface Presenter {
        fun loadMovies()
    }
}