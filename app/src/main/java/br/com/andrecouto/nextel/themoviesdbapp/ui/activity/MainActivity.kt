package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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

class MainActivity : AppCompatActivity(), MainScreenContract.View   {

    @Inject
    internal lateinit var mainPresenter: MainScreenPresenter
    internal lateinit var adapter: MovieAdapter
    internal lateinit var linearLayoutManager:LinearLayoutManager

    val PAGE_START = 1
    var isLoadingInner = false
    var isLastPageInner = false
    val TOTAL_PAGES = 200
    var currentPage = PAGE_START

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
                isLoadingInner = true;
                currentPage += 1;
                loadNextPage()
            }
            override val totalPageCount: Int
                get() = TOTAL_PAGES
        })

        error_btn_retry.setOnClickListener {
            hideErrorView();
            getMovies()
        }

        getMovies()
    }

    fun getMovies() {
        val dao = DatabaseManager.getMovieDAO()
        doAsync {
            thread {
                if (dao.findAll().size >= currentPage * 20) {
                    addMovies(dao.findNext(currentPage * 20))
                } else {
                    mainPresenter.loadMovies(currentPage)
                }
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
    }

    override fun showComplete() {
        Log.e("complete", "completed")
    }

    fun loadNextPage() {
        isLoadingInner = false
        if (currentPage !== TOTAL_PAGES)
            adapter.addLoadingFooter()
        else
            isLastPageInner = true
        getMovies()
    }

    override fun showPosts(posts: MovieResponse?) {
        val dao = DatabaseManager.getMovieDAO()
        doAsync {
            thread {
                dao.insert(posts!!.movieList)
            }
           addMovies(dao.findNext(currentPage))
        }
    }

    fun addMovies(movies: List<Movie>) {
        doAsync {
            uiThread {
                adapter.clear()
                adapter.addAll(movies)
                adapter.removeLoadingFooter()
                adapter.notifyDataSetChanged()
                main_progress.visibility = View.GONE
            }
        }
    }
}
