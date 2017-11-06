package br.com.andrecouto.nextel.themoviesdbapp.data.api

import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import retrofit2.http.*
import rx.Observable


interface MovieAPI {

    @GET("/3/movie/now_playing")
    fun getMovies(
            @Query("api_key") apikey:String,
            @Query("language") language:String,
            @Query("page") page:Int):Observable<MovieResponse>
}