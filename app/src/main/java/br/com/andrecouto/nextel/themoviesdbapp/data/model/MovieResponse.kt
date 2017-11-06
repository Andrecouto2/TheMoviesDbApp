package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("results")
    lateinit var movieList : ArrayList<Movie>
    @SerializedName("total_pages")
    var totalPages : Int = 0
}