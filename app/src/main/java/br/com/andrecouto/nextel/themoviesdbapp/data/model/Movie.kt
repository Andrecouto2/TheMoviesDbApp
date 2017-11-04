package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "movie")
class Movie(id: Int, voteCount: Int, video: Boolean, voteAverage: Double,
            title: String, popularity: Double, posterPath: String, originalLanguage: String,
            originalTitle: String, backdropPath: String, adult: Boolean, overview: String,
            releaseDate: Date, genreIds: IntArray) : Parcelable {

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
    var posterPath: String = ""

    @SerializedName("original_language")
    var originalLanguage: String = ""

    @SerializedName("original_title")
    var originalTitle: String = ""

    @SerializedName("backdrop_path")
    var backdropPath: String = ""

    var adult: Boolean = false

    var overview: String = ""

    @SerializedName("release_date")
    @Ignore
    var releaseDate: Date = Date()

    @SerializedName("genre_ids")
    @Ignore
    var genreIds: IntArray = intArrayOf()

    constructor() : this(0, 0, false, 0.0, "", 0.0, "", "", "", "", false, "", Date(), intArrayOf())

    override fun toString(): String {
        return "Movie{title='$title'}"
    }

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
        this.genreIds = genreIds
    }


    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            1 == source.readInt(),
            source.readDouble(),
            source.readString(),
            source.readDouble(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readSerializable() as Date,
            source.createIntArray()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeInt(voteCount)
        writeInt((if (video) 1 else 0))
        writeDouble(voteAverage)
        writeString(title)
        writeDouble(popularity)
        writeString(posterPath)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeString(backdropPath)
        writeInt((if (adult) 1 else 0))
        writeString(overview)
        writeSerializable(releaseDate)
        writeIntArray(genreIds)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}