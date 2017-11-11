package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genres")
class Genre(genreId: Int, movieId: Int, name: String) : Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var genreId: Int = 0

    var movieId: Int = 0

    var name: String = ""

    init {
        this.genreId = genreId
        this.movieId = movieId
        this.name = name
    }

    constructor() : this(0, 0, "")

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(genreId)
        writeInt(movieId)
        writeString(name)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Genre> = object : Parcelable.Creator<Genre> {
            override fun createFromParcel(source: Parcel): Genre = Genre(source)
            override fun newArray(size: Int): Array<Genre?> = arrayOfNulls(size)
        }
    }
}