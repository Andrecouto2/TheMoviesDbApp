package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "video")
class Video(id: String, movieId: Int, key: String) : Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var videoId: String = ""

    var movieId: Int = 0

    var key: String = ""

    constructor() : this("", 0, "")

    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(videoId)
        writeInt(movieId)
        writeString(key)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Video> = object : Parcelable.Creator<Video> {
            override fun createFromParcel(source: Parcel): Video = Video(source)
            override fun newArray(size: Int): Array<Video?> = arrayOfNulls(size)
        }
    }
}