package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName

class MovieRespond {
    @SerializedName("results")
    var movieList : List<Movie>? = null
}