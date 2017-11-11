package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "movie")
class Movie(id: Int, voteCount: Int, video: Boolean, voteAverage: Double,
            title: String, popularity: Double, posterPath: String?, originalLanguage: String?,
            originalTitle: String, backdropPath: String?, adult: Boolean, overview: String?,
            releaseDate: Date?, homepage: String?, runtime: Int?) :Parcelable {

    @PrimaryKey
    var id: Int = 0

    @SerializedName("vote_count")
    var voteCount: Int = 0

    var video: Boolean = false

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0

    var title: String = ""

    var popularity: Double = 0.0

    @SerializedName("poster_path")
    var posterPath: String? = ""

    @SerializedName("original_language")
    var originalLanguage: String? = ""

    @SerializedName("original_title")
    var originalTitle: String? = ""

    @SerializedName("backdrop_path")
    var backdropPath: String? = ""

    var adult: Boolean = false

    var overview: String? = ""

    @SerializedName("release_date")
    @Ignore
    var releaseDate: Date? = Date()

    var homepage: String? = ""

    var runtime: Int? = 0

    @SerializedName("genres")
    @Ignore
    var genres: ArrayList<Genre> = ArrayList()

    init {
        this.id = id
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
        this.homepage = homepage
        this.runtime = runtime
    }

    constructor() : this(0, 0, false, 0.0, "", 0.0, "", "", "", "", false, "", Date(), "", 0)

    override fun toString(): String {
        return "Movie{title='$title', voteAverage='$voteAverage'}"
    }

    constructor(source: Parcel) : this(
            source.readValue(Int::class.java.classLoader) as Int,
            source.readValue(Int::class.java.classLoader) as Int,
            source.readValue(Boolean::class.java.classLoader) as Boolean,
            source.readValue(Double::class.java.classLoader) as Double,
            source.readString(),
            source.readValue(Double::class.java.classLoader) as Double,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean,
            source.readString(),
            source.readSerializable() as Date?,
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(voteCount)
        writeValue(video)
        writeValue(voteAverage)
        writeString(title)
        writeValue(popularity)
        writeString(posterPath)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeString(backdropPath)
        writeValue(adult)
        writeString(overview)
        writeSerializable(releaseDate)
        writeString(homepage)
        writeValue(runtime)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}