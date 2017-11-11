package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Cast
import io.reactivex.Maybe

@Dao
interface CastDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cast: Cast)

}
