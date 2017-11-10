package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import android.content.res.Resources
import android.util.Log
import br.com.andrecouto.nextel.themoviesdbapp.data.api.MovieAPI
import br.com.andrecouto.nextel.themoviesdbapp.data.model.CastResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieResponse
import br.com.andrecouto.nextel.themoviesdbapp.data.model.VideoResponse
import javax.inject.Inject;
import retrofit2.Retrofit;
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainScreenPresenter @Inject
constructor(retrofit: Retrofit, dView: MainScreenContract.View,
            pnMoviesView: MainScreenContract.PlayingNowMoviesView,
            dMoviesView: MainScreenContract.DetailsMovieView,
            vMoviesView: MainScreenContract.VideosMovieView,
            cMoviesView: MainScreenContract.CastsMovieView) : MainScreenContract.Presenter {

    var retrofit: Retrofit
    internal var dView: MainScreenContract.View
    internal var pnMoviesView: MainScreenContract.PlayingNowMoviesView
    internal var dMoviesView: MainScreenContract.DetailsMovieView
    internal var vMoviesView: MainScreenContract.VideosMovieView
    internal var cMoviesView: MainScreenContract.CastsMovieView

    init {
        this.retrofit = retrofit
        this.dView = dView
        this.pnMoviesView = pnMoviesView
        this.dMoviesView = dMoviesView
        this.vMoviesView = vMoviesView
        this.cMoviesView = cMoviesView
    }

    override fun loadMovies(page: Int) {
        retrofit.create(MovieAPI::class.java).getMovies(Constants.API_KEY, Resources.getSystem().getConfiguration().locale.getLanguage(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(object : Observer<MovieResponse> {
                    override fun onError(e: Throwable) {
                        if (e != null) {
                            dView.showError(e.message!!)
                        }
                    }

                    override fun onNext(t: MovieResponse) {
                        pnMoviesView.showMovies(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                       dView.onSubscribe(d)
                    }

                    override fun onComplete() {
                        dView.showComplete()
                    }

                })

    }

    override fun getDetailsMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMovieDetails(movieId, Resources.getSystem().getConfiguration().locale.getLanguage(), Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({movies: Movie? ->
                    dMoviesView.showDetailsMovie(movies)
                }, { t: Throwable? -> dView.showError(t!!.message.toString())})

    }

    override fun getCastsMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMovieCasts(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({casts: CastResponse? ->
                    cMoviesView.showCastsMovies(casts)
                }, { t: Throwable? -> dView.showError(t!!.message.toString())})
    }

    override fun getVideoMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMoviesVideos(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({videos: VideoResponse? ->
                    vMoviesView.showVideosMovies(videos)
                }, { t: Throwable? ->
                    dView.showError(t!!.message.toString())})
    }

}




