package br.com.andrecouto.nextel.themoviesdbapp.ui.contract

import br.com.andrecouto.nextel.themoviesdbapp.data.model.*

class SearchScreenContract {

    interface searchView {
        fun onShowMovieLile(movie: List<Movie>)
        fun showDetailsMovie(movie: Movie)
        fun showCastsMovies(casts: CastResponse)
        fun showVideosMovies(videos: VideoResponse)
        fun onShowMovieWith(movieWith: MovieWithCastGenreVideo)
    }

    interface Presenter {
        fun getLocalDataMovieLike(query: String)
        fun getDetailsMovie(movieId: Int)
        fun getCastsMovie(movieId: Int)
        fun getVideoMovie(movieId: Int)
        fun getLocalDataMovieWith(moviedId: Int)
        fun setLocalDataUpdateMovie(movie: Movie)
        fun setLocalDataCasts(casts: List<Cast>, movieId: Int)
        fun setLocalDataGenres(casts: List<Genre>, movieId: Int)
        fun setLocalDataVideos(casts: List<Video>, movieId: Int)
    }
}