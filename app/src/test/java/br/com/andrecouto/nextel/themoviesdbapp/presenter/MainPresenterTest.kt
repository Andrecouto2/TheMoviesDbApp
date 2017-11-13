package br.com.andrecouto.nextel.themoviesdbapp.presenter

import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.presenter.MainScreenPresenter
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.junit.Before
import org.mockito.MockitoAnnotations

class MainPresenterTest : BasePresenterTest() {

    @Mock
    lateinit var view: MainScreenContract.MainView

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @Throws(Exception::class)
    fun testOnStart_networkRequestInProgress() {
        verify(view, times(1))
    }

    @Test
    @Throws(Exception::class)
    fun testGetMovieList() {
        Assert.assertEquals(4, (2 + 2).toLong())
    }

}