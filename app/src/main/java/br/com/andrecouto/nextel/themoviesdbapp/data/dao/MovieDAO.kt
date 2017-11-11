package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.*
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import android.arch.persistence.room.Update
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieWithCastGenreVideo
import io.reactivex.Maybe

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie where id = :arg0")
    fun getById(id: Int): Maybe<MovieWithCastGenreVideo>

    @Query("SELECT * FROM movie order by title asc")
    fun findAll(): Maybe<List<Movie>>

    @Query("SELECT * FROM movie where voteAverage > 5.0 order by title asc LIMIT :arg0,:arg1")
    fun findNext(first: Int, last: Int): Maybe<List<Movie>>

    @Query("SELECT * FROM movie WHERE title LIKE :arg0 and voteAverage > 5.0 order by title asc")
    fun findLike(query: String): Maybe<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: ArrayList<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)
}