package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.Html
import android.view.Menu
import br.com.andrecouto.nextel.themoviesdbapp.R
import br.com.andrecouto.nextel.themoviesdbapp.adapter.MovieAdapter
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.util.SearchUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchResultsActivity : AppCompatActivity(), SearchView.OnQueryTextListener{

    internal lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search_recycler.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        search_recycler.setItemAnimator(DefaultItemAnimator())
        adapter = MovieAdapter(applicationContext, ArrayList()) {}
        search_recycler.adapter = adapter
        handleIntent(getIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.menu_movies, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).getActionView() as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()))
        searchView.setOnQueryTextListener(this)
        searchView.setIconifiedByDefault(false)
        return true
    }

    fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            filterMovies(query)
        }
    }

    fun filterMovies(query: String) {
        val dao = DatabaseManager.getMovieDAO()
        dao.findLike("%"+query+"%")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfMovies ->
                    adapter.addAll(listOfMovies)
                    txt_result_search.setText(Html.fromHtml(SearchUtils.getResultSearchString(this, listOfMovies.size)))
                }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        adapter.clear()
        filterMovies(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}