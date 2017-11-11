package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import br.com.andrecouto.nextel.themoviesdbapp.App
import br.com.andrecouto.nextel.themoviesdbapp.R
import br.com.andrecouto.nextel.themoviesdbapp.adapter.MovieAdapter
import br.com.andrecouto.nextel.themoviesdbapp.data.component.DaggerMainScreenComponent
import br.com.andrecouto.nextel.themoviesdbapp.data.model.*
import br.com.andrecouto.nextel.themoviesdbapp.data.module.MainScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.listener.PaginationScrollListener
import br.com.andrecouto.nextel.themoviesdbapp.ui.presenter.MainScreenPresenter
import br.com.andrecouto.nextel.themoviesdbapp.util.NetworkUtils
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainScreenContract.MainView {

    @Inject
    internal lateinit var mainPresenter: MainScreenPresenter
    internal lateinit var adapter: MovieAdapter
    internal lateinit var linearLayoutManager: LinearLayoutManager

    val pagination = 20
    var isLoadingInner = false
    var isLastPageInner = false
    var totalPages = 1
    var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this, Crashlytics())
        DaggerMainScreenComponent.builder()
                .netComponent((getApplicationContext() as App).netComponent)
                .mainScreenModule(MainScreenModule(this))
                .build().inject(this)

        supportActionBar.apply { title = "" }
        initViews()
        mainPresenter.getLocalDataAllMovies()
    }

    fun initViews() {
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainRecycler.setLayoutManager(linearLayoutManager)
        mainRecycler.setItemAnimator(DefaultItemAnimator())
        adapter = MovieAdapter(applicationContext, ArrayList()) { onClickMovie(it) }
        mainRecycler.adapter = adapter
        mainRecycler.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override val isLastPage: Boolean
                get() = isLastPageInner
            override val isLoading: Boolean
                get() = isLoadingInner

            override fun loadMoreItems() {
                if (!isLoadingInner) {
                    loadNextPage()
                    isLoadingInner = true
                    currentPage += 1
                }
            }

            override val totalPageCount: Int
                get() = totalPages
        })

        btnErrorRetry.setOnClickListener {
            hideErrorView();
            mainPresenter.getLocalDataAllMovies()
        }

        mainPresenter.dView.onMovieLocalDataCountPage(pagination)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_movies, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(this, SearchResultsActivity::class.java)))
        return true
    }

    private fun hideErrorView() {
        if (errorLayout.visibility === View.VISIBLE) {
            errorLayout.setVisibility(View.GONE)
            mainProgress.setVisibility(View.VISIBLE)
        }
    }

    fun loadNextPage() {
        if (currentPage <= totalPages)
            adapter.addLoadingFooter()
        else
            isLastPageInner = true
        mainPresenter.getLocalDataAllMovies()
    }

    fun addMovies(movies: List<Movie>) {
        adapter.addAll(movies)
        adapter.removeLoadingFooter()
        mainProgress.visibility = View.GONE
    }

    open fun onClickMovie(movie: Movie) {
        if (!NetworkUtils.isNetworkAvailable(this))
            startActivity<DetailsMovieActivity>("movie" to movie)

        mainPresenter.getLocalDataMovieWith(movie.id)
    }

    override fun showError(message: String) {
        errorLayout.visibility = View.VISIBLE
        tErrorCause.setText(message)
        mainProgress.visibility = View.GONE
        isLoadingInner = false
        if (currentPage > 1) {
            currentPage -= 1
        }
    }

    override fun showComplete() {
        Log.e("complete", "completed")
        isLoadingInner = false
    }

    override fun showMovies(movies: MovieResponse) {
        totalPages = movies!!.totalPages
        mainPresenter.setLocalDataMovies(movies.movieList!!)
        addMovies(movies!!.movieList.filter { movie -> movie.voteAverage!! > 5.0 })
    }

    override fun showDetailsMovie(movie: Movie) {
        mainPresenter.setLocalDataUpdateMovie(movie!!)
        mainPresenter.setLocalDataGenres(movie!!.genres!!, movie.id)
        mainProgress.visibility = View.GONE
        startActivity<DetailsMovieActivity>("movie" to movie!!)
    }

    override fun showCastsMovies(casts: CastResponse) {
        mainPresenter.setLocalDataCasts(casts.castList, casts.id)
        mainPresenter.getVideoMovie(casts!!.id)
    }

    override fun showVideosMovies(videos: VideoResponse) {
            mainPresenter.setLocalDataVideos(videos.videoList, videos.id)
            mainPresenter.getDetailsMovie(videos!!.id)
    }

    override fun onMovieLocalDataCountPage(count: Int) {
         totalPages = count
    }

    override fun onShowNextMovies(movies: List<Movie>) {
        addMovies(movies)
        isLoadingInner = false
    }

    override fun onShowAllMovies(movies: List<Movie>) {
        if (movies.size >= currentPage * pagination) {
            mainPresenter.getLocalDataNextMovies(currentPage, pagination)
        } else {
            mainPresenter.loadMovies(currentPage)
        }
    }

    override fun onShowMovieWith(movieWith: MovieWithCastGenreVideo) {
        if (movieWith.casts.size > 0 || movieWith.genres.size > 0 || movieWith.videos.size > 0) {
            startActivity<DetailsMovieActivity>("movie" to movieWith.movie)
        } else {
            mainProgress.visibility = View.VISIBLE
            mainPresenter.getCastsMovie(movieWith.movie.id)
        }
    }
}
