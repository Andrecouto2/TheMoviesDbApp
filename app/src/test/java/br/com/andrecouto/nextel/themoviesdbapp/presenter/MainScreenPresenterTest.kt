package br.com.andrecouto.nextel.themoviesdbapp.presenter

import br.com.andrecouto.nextel.themoviesdbapp.data.component.NetComponent
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.presenter.MainScreenPresenter
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by andrecouto on 14/11/2017.
 */
class MainScreenPresenterTest : BasePresenterTest() {

    @Mock
    lateinit var view: MainScreenContract.MainView
    internal lateinit var presenter: MainScreenPresenter

    @Before
    fun setUp() {
           MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetRetrofit() {}
    @Test
    fun testSetRetrofit() {}
    @Test
    fun `testGetDView$production_sources_for_module_app`() {}
    @Test
    fun `testSetDView$production_sources_for_module_app`() {}
    @Test
    fun testLoadMovies() {}
    @Test
    fun testGetDetailsMovie() {}
    @Test
    fun testGetCastsMovie() {}
    @Test
    fun testGetVideoMovie() {}
    @Test
    fun testGetLocalDataCountMovie() {}
    @Test
    fun testGetLocalDataNextMovies() {}
    @Test
    fun testGetLocalDataAllMovies() {}
    @Test
    fun testGetLocalDataMovieWith() {}
    @Test
    fun testSetLocalDataMovies() {}
    @Test
    fun testSetLocalDataCasts() {}
    @Test
    fun testSetLocalDataGenres() {}
    @Test
    fun testSetLocalDataVideos() {}
    @Test
    fun testSetLocalDataUpdateMovie() {}

}