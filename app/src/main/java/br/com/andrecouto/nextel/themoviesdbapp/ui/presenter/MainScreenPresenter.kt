package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import br.com.andrecouto.nextel.themoviesdbapp.data.api.MovieAPI
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import javax.inject.Inject;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import rx.Observable

class MainScreenPresenter @Inject
constructor(retrofit: Retrofit, mView: MainScreenContract.View) : MainScreenContract.Presenter {
    var retrofit: Retrofit
    internal var mView: MainScreenContract.View

    init {
        this.retrofit = retrofit
        this.mView = mView
    }

    override fun loadMovies() {
        retrofit.create(MovieAPI::class.java).movieList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<Movie>> {
                    override fun onCompleted() {
                        mView.showComplete()
                    }

                    override fun onError(e: Throwable?) {
                        if (e != null) {
                            mView.showError(e.message!!)
                        }
                    }

                    override fun onNext(t: List<Movie>?) {
                        mView.showPosts(t)
                    }

                })

    }
}

private fun <T> Observable<T>.subscribe(observer: Observer<List<Movie>>) {}



