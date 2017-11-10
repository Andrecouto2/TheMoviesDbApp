package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.Room
import br.com.andrecouto.nextel.themoviesdbapp.App

object DatabaseManager {
    // Singleton do Room: banco de dados
    private var dbInstance: MoviesDatabase

    init {
        val appContext = App.getInstance().applicationContext

        // Configura o Room
        dbInstance = Room.databaseBuilder(
                appContext,
                MoviesDatabase::class.java,
                "movies.sqlite")
                .build()
    }

    fun getMovieDAO(): MovieDAO {
        return dbInstance.movieDAO()
    }

    fun getCastDAO(): CastDAO {
        return dbInstance.castDAO()
    }

    fun getVideoDAO(): VideoDAO {
        return dbInstance.videoDAO()
    }
}