package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("results")
    lateinit var movieList : ArrayList<Movie>
}