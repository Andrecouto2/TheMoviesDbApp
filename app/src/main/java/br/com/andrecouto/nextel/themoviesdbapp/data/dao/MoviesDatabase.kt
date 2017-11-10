package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Cast
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Video

@Database(entities = arrayOf(Movie::class, Cast::class, Video::class), version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
    abstract fun castDAO(): CastDAO
    abstract fun videoDAO(): VideoDAO
}