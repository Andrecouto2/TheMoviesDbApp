package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse

interface MainScreenContract {
    interface View {
        fun showPosts(posts: MovieResponse?)
        fun showError(message: String)
        fun showComplete()
    }

    interface Presenter {
        fun loadMovies(page : Int)
    }
}