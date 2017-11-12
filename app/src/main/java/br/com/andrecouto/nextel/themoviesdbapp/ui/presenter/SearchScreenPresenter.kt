package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import android.content.res.Resources
import br.com.andrecouto.nextel.themoviesdbapp.data.api.MovieAPI
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.data.model.*
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.SearchScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
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

    override fun getDetailsMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMovieDetails(movieId, Resources.getSystem().getConfiguration().locale.getLanguage(), Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({ movie: Movie ->
                    searchView.showDetailsMovie(movie)
                })

    }

    override fun getCastsMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMovieCasts(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({ casts: CastResponse ->
                    searchView.showCastsMovies(casts)
                })
    }

    override fun getVideoMovie(movieId: Int) {
        retrofit.create(MovieAPI::class.java).getMoviesVideos(movieId, Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe({ videos: VideoResponse ->
                    searchView.showVideosMovies(videos)
                })
    }

    override fun getLocalDataMovieWith(moviedId: Int) {
        DatabaseManager.getMovieDAO().getById(moviedId)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { movieWith ->
                    searchView.onShowMovieWith(movieWith)
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
