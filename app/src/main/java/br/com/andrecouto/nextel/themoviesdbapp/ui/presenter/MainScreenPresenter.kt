package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import android.util.Log
import br.com.andrecouto.nextel.themoviesdbapp.data.api.MovieAPI
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import javax.inject.Inject;
import retrofit2.Retrofit;
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainScreenPresenter @Inject
constructor(retrofit: Retrofit, mView: MainScreenContract.View) : MainScreenContract.Presenter {

    var retrofit: Retrofit
    internal var mView: MainScreenContract.View

    init {
        this.retrofit = retrofit
        this.mView = mView
    }

    override fun loadMovies(page: Int) {
        retrofit.create(MovieAPI::class.java).getMovies(Constants.API_KEY, "en-US", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<MovieResponse> {
                    override fun onError(e: Throwable) {
                        if (e != null) {
                            mView.showError(e.message!!)
                        }
                    }

                    override fun onNext(t: MovieResponse) {
                        mView.showPosts(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                       Log.e("",d.toString())
                    }

                    override fun onComplete() {
                        mView.showComplete()
                    }

                })

    }

}




