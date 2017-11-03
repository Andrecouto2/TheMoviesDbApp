package br.com.andrecouto.nextel.themoviesdbapp.data.api

import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import retrofit2.http.GET
import rx.Observable


interface MovieAPI {

    @get:GET("/posts")
    val movieList: Observable<List<Movie>>
}