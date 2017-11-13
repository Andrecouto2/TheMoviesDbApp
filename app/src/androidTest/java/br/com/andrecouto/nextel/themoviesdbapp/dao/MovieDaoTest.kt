package br.com.andrecouto.nextel.themoviesdbapp.dao

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.MovieDAO
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.MoviesDatabase
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.ui.activity.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by andrecouto on 13/11/2017.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieDaoTest {

    lateinit var movieDao: MovieDAO
    lateinit var database: MoviesDatabase

    @get:Rule
    val mActivityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

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
    @Throws(Exception::class)
    fun testInsertedAndRetrievedMoviesMatch() {
        val users = arrayListOf(Movie(1, 0, false, 0.0, "Titanic", 0.0, "", "", "", "", false, "", Date(), "", 0), Movie())
        movieDao.insert(users)

        movieDao.findAll().doOnSuccess {
              t: List<Movie> ->
            assertEquals(users, t)
        }

    }

}