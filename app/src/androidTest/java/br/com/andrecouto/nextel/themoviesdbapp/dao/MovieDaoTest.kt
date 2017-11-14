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
import kotlin.collections.ArrayList

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

    @Test
    @Throws(Exception::class)
    fun testConflictingInsertsReplaceMovies() {
        val movies = arrayListOf(
                Movie(1, 0, false, 0.0, "Titanic1", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(2, 0, false, 0.0, "Titanic2", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(3, 0, false, 0.0, "Titanic3", 0.0, "", "", "", "", false, "", Date(), "", 0))

        val movies2 = arrayListOf(
                Movie(1, 0, false, 0.0, "Titanic1", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(2, 0, false, 0.0, "Titanic2", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(4, 0, false, 0.0, "Titanic4", 0.0, "", "", "", "", false, "", Date(), "", 0))

        val expectedMovies = arrayListOf(
                Movie(1, 0, false, 0.0, "Titanic1", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(2, 0, false, 0.0, "Titanic2", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(3, 0, false, 0.0, "Titanic3", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(4, 0, false, 0.0, "Titanic4", 0.0, "", "", "", "", false, "", Date(), "", 0))

        movieDao.insert(movies)
        movieDao.insert(movies2)
        movieDao.findAll().doOnSuccess { t: List<Movie> ->
            assertEquals(expectedMovies, t)
        }
    }

    @Test
    @Throws(Exception::class)
    fun testLimitMoviesPerPage() {
        val movies = arrayListOf(
                Movie(1, 0, false, 0.0, "Titanic1", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(2, 0, false, 0.0, "Titanic2", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(3, 0, false, 0.0, "Titanic3", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(4, 0, false, 0.0, "Titanic4", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(5, 0, false, 0.0, "Titanic5", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(6, 0, false, 0.0, "Titanic6", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(7, 0, false, 0.0, "Titanic7", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(8, 0, false, 0.0, "Titanic8", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(9, 0, false, 0.0, "Titanic9", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(10, 0, false, 0.0, "Titanic10", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(11, 0, false, 0.0, "Titanic11", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(12, 0, false, 0.0, "Titanic12", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(13, 0, false, 0.0, "Titanic13", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(14, 0, false, 0.0, "Titanic14", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(15, 0, false, 0.0, "Titanic15", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(16, 0, false, 0.0, "Titanic16", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(17, 0, false, 0.0, "Titanic17", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(18, 0, false, 0.0, "Titanic18", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(19, 0, false, 0.0, "Titanic19", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(20, 0, false, 0.0, "Titanic20", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(21, 0, false, 0.0, "Titanic21", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(22, 0, false, 0.0, "Titanic22", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(23, 0, false, 0.0, "Titanic23", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(24, 0, false, 0.0, "Titanic24", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(25, 0, false, 0.0, "Titanic25", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(26, 0, false, 0.0, "Titanic26", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(27, 0, false, 0.0, "Titanic27", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(28, 0, false, 0.0, "Titanic28", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(29, 0, false, 0.0, "Titanic29", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(30, 0, false, 0.0, "Titanic30", 0.0, "", "", "", "", false, "", Date(), "", 0))

        movieDao.insert(movies)

        movieDao.findNext(1,20).doOnSuccess { t: List<Movie> ->
            assertEquals(20, t.size)
        }

    }

    @Test
    @Throws(Exception::class)
    fun testSearchMovies() {
        val movies = arrayListOf(
                Movie(1, 0, false, 0.0, "Titanic1", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(2, 0, false, 0.0, "Titanic2", 0.0, "", "", "", "", false, "", Date(), "", 0),
                Movie(3, 0, false, 0.0, "Titanic3", 0.0, "", "", "", "", false, "", Date(), "", 0))


        movieDao.insert(movies)

        movieDao.findLike("%1%").doOnSuccess { t: List<Movie> ->
            assertEquals(movies.get(0), t.get(0))
        }
    }


}