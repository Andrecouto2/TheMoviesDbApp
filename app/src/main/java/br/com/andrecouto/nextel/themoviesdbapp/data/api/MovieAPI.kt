package br.com.andrecouto.nextel.themoviesdbapp.data.api

import br.com.andrecouto.nextel.themoviesdbapp.data.model.CastResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.model.VideoResponse
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.*

interface MovieAPI {

    @GET("/3/movie/now_playing")
    fun getMovies(
            @Query("api_key") apikey:String,
            @Query("language") language:String,
            @Query("page") page:Int): Observable<MovieResponse>

    @GET("/3/movie/{movie_id}")
    fun getMovieDetails(
            @Path("movie_id") movieId:Int,
            @Query("language") language:String,
            @Query("api_key") apikey:String
    ): Maybe<Movie>

    @GET("/3/movie/{movie_id}/credits")
    fun getMovieCasts(
            @Path("movie_id") movieId:Int,
            @Query("api_key") apikey:String
    ): Maybe<CastResponse>

    @GET("/3/movie/{movie_id}/videos")
    fun getMoviesVideos(
            @Path("movie_id") movieId:Int,
            @Query("api_key") apikey:String
    ): Maybe<VideoResponse>
}