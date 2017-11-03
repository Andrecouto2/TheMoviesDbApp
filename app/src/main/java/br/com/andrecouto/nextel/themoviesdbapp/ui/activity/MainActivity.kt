package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.andrecouto.nextel.themoviesdbapp.App
import br.com.andrecouto.nextel.themoviesdbapp.R
import br.com.andrecouto.nextel.themoviesdbapp.data.component.DaggerMainScreenComponent
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.data.model.MovieRespond
import br.com.andrecouto.nextel.themoviesdbapp.data.module.MainScreenModule
import br.com.andrecouto.nextel.themoviesdbapp.ui.contract.MainScreenContract
import br.com.andrecouto.nextel.themoviesdbapp.ui.presenter.MainScreenPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainScreenContract.View   {

    @Inject
    internal lateinit var mainPresenter: MainScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainScreenComponent.builder()
                .netComponent((getApplicationContext() as App).netComponent)
                .mainScreenModule(MainScreenModule(this))
                .build().inject(this)
        mainPresenter.loadMovies()
    }

    override fun showError(message: String) {
        Log.e("error", message)
    }

    override fun showComplete() {
        Log.e("complete", "completed")
    }

    override fun showPosts(posts: MovieRespond?) {
        Log.e("moviex", posts!!.movieList!!.get(0).title)
    }


}
