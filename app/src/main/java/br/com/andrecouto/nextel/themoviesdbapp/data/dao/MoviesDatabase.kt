package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie

@Database(entities = arrayOf(Movie::class), version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
}