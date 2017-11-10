package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cast")
class Cast(castId: Int, movieId: Int, name: String, order: Int) : Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var castId: Int = 0

    var movieId: Int = 0

    var name: String = ""

    @SerializedName("order")
    var position: Int = 0

    constructor() : this(0, 0, "", 0)

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(castId)
        writeInt(movieId)
        writeString(name)
        writeInt(position)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Cast> = object : Parcelable.Creator<Cast> {
            override fun createFromParcel(source: Parcel): Cast = Cast(source)
            override fun newArray(size: Int): Array<Cast?> = arrayOfNulls(size)
        }
    }
}