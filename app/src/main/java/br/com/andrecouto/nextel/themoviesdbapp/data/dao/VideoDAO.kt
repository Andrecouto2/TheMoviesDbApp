package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Video
import io.reactivex.Maybe

@Dao
interface VideoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(video: Video)

    @Query("SELECT * FROM video where movieid = :arg0 ")
    fun findAllById(moveId: Int) : Maybe<List<Video>>
}