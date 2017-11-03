package br.com.andrecouto.nextel.themoviesdbapp.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Movie(movieId: Int, voteCount: Int, video: Boolean, voteAverage: Double,
            title: String, popularity: Double, posterPath: String, originalLanguage: String,
            originalTitle: String, backdropPath: String, adult: Boolean, overview: String,
            releaseDate: Date, genreIds: List<Int>) {

    @SerializedName("id")
    var movieId: Int
    @SerializedName("vote_count")
    var voteCount: Int
    var video: Boolean
    @SerializedName("vote_average")
    var voteAverage: Double
    var title: String
    var popularity: Double
    @SerializedName("poster_path")
    var posterPath: String
    @SerializedName("original_language")
    var originalLanguage: String
    @SerializedName("original_title")
    var originalTitle: String
    @SerializedName("backdrop_path")
    var backdropPath: String
    var adult: Boolean
    var overview: String
    @SerializedName("release_date")
    var releaseDate: Date
    @SerializedName("genre_ids")
    var genreIds: List<Int>

    init {
        this.movieId = movieId
        this.voteCount = voteCount
        this.video = video
        this.voteAverage = voteAverage
        this.title = title
        this.popularity = popularity
        this.posterPath = posterPath
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.backdropPath = backdropPath
        this.adult = adult
        this.overview = overview
        this.releaseDate = releaseDate
        this.genreIds = genreIds
    }
}