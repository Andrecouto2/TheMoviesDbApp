package br.com.andrecouto.nextel.themoviesdbapp.data.dao

import android.arch.persistence.room.*
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

@Dao
interface MovieDAO {

    @Query("SELECT * FROM movie where id = :arg0")
    fun getById(id: Int): Movie?

    @Query("SELECT * FROM movie order by title asc")
    fun findAll(): Flowable<List<Movie>>

    //@Query("SELECT * FROM movie where voteAverage > 5.0 order by voteAverage desc LIMIT :arg0")
    @Query("SELECT * FROM movie order by title asc LIMIT :arg0,:arg1")
    fun findNext(first: Int, last: Int): Flowable<List<Movie>>

    @Query("SELECT * FROM movie WHERE title LIKE :arg0 order by title asc")
    fun findLike(query: String): Flowable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: ArrayList<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovie(movie: Movie)
}