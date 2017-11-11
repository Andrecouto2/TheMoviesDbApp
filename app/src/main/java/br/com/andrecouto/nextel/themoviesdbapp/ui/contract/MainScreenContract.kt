package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.*

interface MainScreenContract {

    interface MainView {
        fun showError(message: String)
        fun showComplete()
        fun showMovies(movies: MovieResponse)
        fun showDetailsMovie(movie: Movie)
        fun showCastsMovies(casts: CastResponse)
        fun showVideosMovies(videos: VideoResponse)
        fun onMovieLocalDataCountPage(count: Int)
        fun onShowNextMovies(movies : List<Movie>)
        fun onShowAllMovies(movies: List<Movie>)
        fun onShowMovieWith(movieWith: MovieWithCastGenreVideo)
    }

    interface Presenter {
        fun loadMovies(page : Int)
        fun getDetailsMovie(movieId: Int)
        fun getCastsMovie(movieId: Int)
        fun getVideoMovie(movieId: Int)
        fun getLocalDataCountMovie(pagination: Int)
        fun getLocalDataNextMovies(currentPage: Int, pagination: Int)
        fun getLocalDataAllMovies()
        fun getLocalDataMovieWith(moviedId: Int)
        fun setLocalDataMovies(movies: ArrayList<Movie>)
        fun setLocalDataUpdateMovie(movie: Movie)
        fun setLocalDataCasts(casts: List<Cast>, movieId: Int)
        fun setLocalDataGenres(casts: List<Genre>, movieId: Int)
        fun setLocalDataVideos(casts: List<Video>, movieId: Int)
    }
}