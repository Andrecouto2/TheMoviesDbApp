package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.SearchScreenContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class SearchScreenPresenter @Inject
constructor(retrofit: Retrofit, searchView: SearchScreenContract.searchView) : SearchScreenContract.Presenter {


    var retrofit: Retrofit
    internal var searchView: SearchScreenContract.searchView

    init {
        this.retrofit = retrofit
        this.searchView = searchView
    }

    override fun getLocalDataMovieLike(query: String) {
        DatabaseManager.getMovieDAO().findLike("%" + query + "%")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfMovies ->
                    searchView.onShowMovieLile(listOfMovies)
                }
    }
}
