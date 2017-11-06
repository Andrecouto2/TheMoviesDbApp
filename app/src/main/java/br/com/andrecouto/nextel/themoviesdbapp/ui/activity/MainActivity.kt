package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
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
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.module.MainScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.pagination.PaginationScrollListener
import br.com.andrecouto.nextel.themoviesdbapp.ui.presenter.MainScreenPresenter
import br.com.andrecouto.nextel.themoviesdbapp.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import javax.inject.Inject
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), MainScreenContract.View, SearchView.OnQueryTextListener {

    @Inject
    internal lateinit var mainPresenter: MainScreenPresenter
    internal lateinit var adapter: MovieAdapter
    internal lateinit var linearLayoutManager:LinearLayoutManager

    val pagination = 20
    var isLoadingInner = false
    var isLastPageInner = false
    var isAlreadyResquested = false
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
                    currentPage += 1
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
        supportActionBar.apply { title = "" }
        getMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_movies, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    fun getTotalOfPages(count: Int): Int {
        if (count == 0) {
            return 1
        }
        if (count % pagination == 0) {
            return (count / pagination) + 1
        } else {
            var i: Double = (count / pagination).toDouble()
            return Math.round(i).toInt() + 1
        }
    }

    fun getMovies() {
        val dao = DatabaseManager.getMovieDAO()
        doAsync {
            thread {
                if (dao.findAll().size >= currentPage * pagination) {
                    addMovies(getMoviesPagination(currentPage))
                } else {
                    mainPresenter.loadMovies(currentPage)
                }
            }

        }
    }

    fun getMoviesPagination(page: Int) : List<Movie> {
        val movies : List<Movie> = DatabaseManager.getMovieDAO().findAll()
        var retorno : ArrayList<Movie> = ArrayList()
        var i: Int = 0
        var k: Int = page * pagination
        if (page != 1) {
            i = (page-1) * pagination
        }
        for (n in i..k-1) {
            retorno.add(movies[n])
        }
        if (!isAlreadyResquested) {
            totalPages = getTotalOfPages(movies.size)
            isAlreadyResquested = true
        }
        isLoadingInner = false
        return retorno
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
        isAlreadyResquested = true
        totalPages = posts!!.totalPages
        doAsync {
            thread {
                dao.insert(posts!!.movieList)
            }
           addMovies(posts!!.movieList)
        }
    }

    fun addMovies(movies: List<Movie>) {
        doAsync {
            uiThread {
                adapter.addAll(movies)
                adapter.removeLoadingFooter()
                adapter.notifyDataSetChanged()
                main_progress.visibility = View.GONE
            }
        }
    }
}
