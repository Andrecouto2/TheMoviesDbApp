package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.module.MainScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.pagination.PaginationScrollListener
import br.com.andrecouto.nextel.themoviesdbapp.ui.presenter.MainScreenPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MainScreenContract.View {

    @Inject
    internal lateinit var mainPresenter: MainScreenPresenter
    internal lateinit var adapter: MovieAdapter
    internal lateinit var linearLayoutManager:LinearLayoutManager

    val pagination = 20
    var isLoadingInner = false
    var isLastPageInner = false
    var totalPages = 1
    var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainScreenComponent.builder()
                .netComponent((getApplicationContext() as App).netComponent)
                .mainScreenModule(MainScreenModule(this))
                .build().inject(this)
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        main_recycler.setLayoutManager(linearLayoutManager)
        main_recycler.setItemAnimator(DefaultItemAnimator())
        adapter = MovieAdapter(applicationContext, ArrayList()) { onClickMovie(it) }
        main_recycler.adapter = adapter
        main_recycler.addOnScrollListener(object: PaginationScrollListener(linearLayoutManager) {
            override val isLastPage: Boolean
                get() = isLastPageInner
            override val isLoading: Boolean
                get() = isLoadingInner
            override fun loadMoreItems() {
                if (!isLoadingInner) {
                    loadNextPage()
                    isLoadingInner = true
                }
            }
            override val totalPageCount: Int
                get() = totalPages
        })

        error_btn_retry.setOnClickListener {
            hideErrorView();
            getMovies()
        }

       DatabaseManager.getMovieDAO().findAll()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfMovies ->
                    totalPages = Math.round((listOfMovies.size/pagination).toDouble()).toInt()
                }

        supportActionBar.apply { title = "" }

        getMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_movies, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).getActionView() as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(this, SearchResultsActivity::class.java)))
        return true
    }

    fun getMovies() {
        val dao = DatabaseManager.getMovieDAO()
        dao.findAll()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfMovies ->
                    if (listOfMovies.size >= currentPage * pagination) {
                        var start: Int = 0
                        if (currentPage > 1)
                            start = (currentPage - 1) * pagination

                        dao.findNext(start, pagination)?.subscribeOn(Schedulers.io())
                                ?.observeOn(AndroidSchedulers.mainThread())
                                ?.subscribe { listOfNextMovies ->
                                    addMovies(listOfNextMovies)
                                    isLoadingInner = false
                                    currentPage += 1
                                }
                    } else {
                        mainPresenter.loadMovies(currentPage)
                        currentPage += 1
                    }

                }
    }

    open fun onClickMovie(movie: Movie) {

    }

    private fun hideErrorView() {
        if (error_layout.visibility === View.VISIBLE) {
            error_layout.setVisibility(View.GONE)
            main_progress.setVisibility(View.VISIBLE)
        }
    }

    override fun showError(message: String) {
        error_layout.visibility = View.VISIBLE
        error_txt_cause.setText(message)
        main_progress.visibility = View.GONE
        currentPage =- 1
        isLoadingInner = false
    }

    override fun showComplete() {
        Log.e("complete", "completed")
        isLoadingInner = false
    }

    fun loadNextPage() {
        if (currentPage <= totalPages)
            adapter.addLoadingFooter()
        else
            isLastPageInner = true
        getMovies()
    }

    override fun showPosts(posts: MovieResponse?) {
        val dao = DatabaseManager.getMovieDAO()
        totalPages = posts!!.totalPages
        doAsync {
            thread {
                 dao.insert(posts!!.movieList)
            }
            getMovies()
        }
    }

    fun addMovies(movies: List<Movie>) {
        doAsync {
            uiThread {
                adapter.addAll(movies)
                adapter.removeLoadingFooter()
                main_progress.visibility = View.GONE
            }
        }
    }
}
