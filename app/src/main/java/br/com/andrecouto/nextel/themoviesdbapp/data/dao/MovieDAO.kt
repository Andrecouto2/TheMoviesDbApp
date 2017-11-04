package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie


@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie where id = :arg0")
    fun getById(id: Int): Movie?

    @Query("SELECT * FROM movie")
    fun findAll(): List<Movie>

    @Insert
    fun insert(movie: Movie)

    @Insert
    fun insert(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)
}