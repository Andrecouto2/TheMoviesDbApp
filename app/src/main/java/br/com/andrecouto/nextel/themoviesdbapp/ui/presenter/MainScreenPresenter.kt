package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import android.content.res.Resources
import android.util.Log
import br.com.andrecouto.nextel.themoviesdbapp.data.api.MovieAPI
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.data.model.*
import javax.inject.Inject;
import retrofit2.Retrofit;
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class MainScreenPresenter @Inject
constructor(retrofit: Retrofit, dView: MainScreenContract.MainView) : MainScreenContract.Presenter {

    var retrofit: Retrofit
    internal var dView: MainScreenContract.MainView

    init {
        this.retrofit = retrofit
        this.dView = dView
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
                        dView.showMovies(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.e("onSubscribe", d.toString())
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
                .subscribe({ movie: Movie ->
                    dView.showDetailsMovie(movie)
                })

    }

    override fun getCastsMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMovieCasts(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({ casts: CastResponse ->
                    dView.showCastsMovies(casts)
                })
    }

    override fun getVideoMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMoviesVideos(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({ videos: VideoResponse ->
                    dView.showVideosMovies(videos)
                })
    }

    override fun getLocalDataCountMovie(pagination: Int) {
        DatabaseManager.getMovieDAO().findAll()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfMovies ->
                    dView.onMovieLocalDataCountPage(Math.round((listOfMovies.size / pagination).toDouble()).toInt())
                }
    }

    override fun getLocalDataNextMovies(currentPage: Int, pagination: Int) {
        var start: Int = 0
        if (currentPage > 1)
            start = (currentPage - 1) * pagination
        DatabaseManager.getMovieDAO().findNext(start, pagination)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfNextMovies ->
                    dView.onShowNextMovies(listOfNextMovies)
                }
    }

    override fun getLocalDataAllMovies() {
        DatabaseManager.getMovieDAO().findAll()?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { listOfMovies ->
                    dView.onShowAllMovies(listOfMovies)
                }
    }

    override fun getLocalDataMovieWith(moviedId: Int) {
        DatabaseManager.getMovieDAO().getById(moviedId)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { movieWith ->
                    dView.onShowMovieWith(movieWith)
                }
    }

    override fun setLocalDataMovies(movies: ArrayList<Movie>) {
        doAsync {
            DatabaseManager.getMovieDAO().insert(movies)
        }
    }

    override fun setLocalDataCasts(casts: List<Cast>, movieId: Int) {
        doAsync {
            for (cast in casts) {
                cast.movieId = movieId
                DatabaseManager.getCastDAO().insert(cast)
            }
        }
    }

    override fun setLocalDataGenres(genres: List<Genre>, movieId: Int) {
        doAsync {
            for (genre in genres) {
                genre.movieId = movieId
                DatabaseManager.getGenreDAO().insert(genre)
            }
        }
    }

    override fun setLocalDataVideos(videos: List<Video>, movieId: Int) {
        doAsync {
            for (video in videos) {
                video.movieId = movieId
                DatabaseManager.getVideoDAO().insert(video)
            }
        }
    }

    override fun setLocalDataUpdateMovie(movie: Movie) {
        doAsync {
            DatabaseManager.getMovieDAO().updateMovie(movie)
        }
    }

}




