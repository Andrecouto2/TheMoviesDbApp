package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName

class VideoResponse {

    @SerializedName("results")
    lateinit var videoList : ArrayList<Video>
    var id : Int = 0
}