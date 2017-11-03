package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieRespond

interface MainScreenContract {
    interface View {
        fun showPosts(posts: MovieRespond?)
        fun showError(message: String)
        fun showComplete()
    }

    interface Presenter {
        fun loadMovies()
    }
}