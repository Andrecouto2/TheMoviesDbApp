package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.*
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import android.arch.persistence.room.Update




@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie where id = :arg0")
    fun getById(id: Int): Movie?

    @Query("SELECT * FROM movie order by title")
    fun findAll(): List<Movie>

    //@Query("SELECT * FROM movie where voteAverage > 5.0 order by voteAverage desc LIMIT :arg0")
    @Query("SELECT * FROM movie order by title desc LIMIT :arg0")
    fun findNext(first: Int): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movies: ArrayList<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)
}