package br.com.andrecouto.nextel.themoviesdbapp.ui.presenter

import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.DetailsMovieScreenContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject;

class DetailsMovieScreenPresenter @Inject
constructor(dMovieView: DetailsMovieScreenContract.dMovieView) : DetailsMovieScreenContract.Presenter {

    internal var dMovieView: DetailsMovieScreenContract.dMovieView

    init {
        this.dMovieView = dMovieView
    }

    override fun getLocalDataMovieWith(moviedId: Int) {
        DatabaseManager.getMovieDAO().getById(moviedId)?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { movieWith ->
                    dMovieView.onShowMovieWith(movieWith)
                }
    }

}