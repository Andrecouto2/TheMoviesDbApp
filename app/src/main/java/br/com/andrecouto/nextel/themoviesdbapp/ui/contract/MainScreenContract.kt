package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.CastResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.model.VideoResponse
import io.reactivex.disposables.Disposable

interface MainScreenContract {

    interface View {
        fun showError(message: String)
        fun showComplete()
        fun onSubscribe(d: Disposable)
    }

    interface PlayingNowMoviesView {
        fun showMovies(movies: MovieResponse?)
    }

    interface DetailsMovieView {
        fun showDetailsMovie(movie: Movie?)
    }

    interface CastsMovieView {
        fun showCastsMovies(casts: CastResponse?)
    }

    interface VideosMovieView {
        fun showVideosMovies(videos: VideoResponse?)
    }

    interface Presenter {
        fun loadMovies(page : Int)
        fun getDetailsMovie(movieId: Int)
        fun getCastsMovie(movieId: Int)
        fun getVideoMovie(movieId: Int)
    }
}