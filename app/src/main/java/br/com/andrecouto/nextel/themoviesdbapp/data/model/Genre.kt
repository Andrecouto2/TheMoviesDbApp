package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "genre")
class Genre(genreId: Int, movieId: Int, name: String) : Parcelable {

    @PrimaryKey
    var id: Int = 0

    var movieId: Int = 0

    var name: String = ""

    init {
        this.id = genreId
        this.movieId = movieId
        this.name = name
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
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