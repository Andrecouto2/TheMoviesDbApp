package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName

class CastResponse {
    @SerializedName("cast")
    lateinit var castList : ArrayList<Cast>
    var id : Int = 0
}
