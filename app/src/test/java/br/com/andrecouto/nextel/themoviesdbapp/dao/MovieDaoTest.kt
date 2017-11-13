package br.com.andrecouto.nextel.themoviesdbapp.dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.MovieDAO
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.MoviesDatabase
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by andrecouto on 13/11/2017.
 */

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {


    lateinit var movieDao: MovieDAO
    lateinit var database: MoviesDatabase

    constructor()
    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java).build()
        movieDao = database.movieDAO()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertedAndRetrievedMoviesMatch() {
        val users = arrayListOf(Movie(1, 0, false, 0.0, "Titanic", 0.0, "", "", "", "", false, "", Date(), "", 0), Movie())
        movieDao.insert(users)

        val allUsers = movieDao.getById(1)
        assertEquals(users, allUsers)
    }

}