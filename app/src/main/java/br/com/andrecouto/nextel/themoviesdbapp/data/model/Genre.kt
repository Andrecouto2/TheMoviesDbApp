package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName

class Genre(genreId: Int, name: String) {

    @SerializedName("id")
    var genreId: Int
    var name: String

    init {
        this.genreId = genreId
        this.name = name
    }

}