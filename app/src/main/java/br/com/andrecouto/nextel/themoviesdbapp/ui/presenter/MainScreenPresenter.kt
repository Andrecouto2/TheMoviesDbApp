package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import br.com.andrecouto.nextel.themoviesdbapp.data.api.MovieAPI
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import javax.inject.Inject;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import rx.Observable

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
                    override fun onCompleted() {
                        mView.showComplete()
                    }

                    override fun onError(e: Throwable?) {
                        if (e != null) {
                            mView.showError(e.message!!)
                        }
                    }

                    override fun onNext(t: MovieResponse?) {
                        mView.showPosts(t)
                    }

                })

    }
}

private fun <T> Observable<T>.subscribe(observer: Observer<List<Movie>>) {}



