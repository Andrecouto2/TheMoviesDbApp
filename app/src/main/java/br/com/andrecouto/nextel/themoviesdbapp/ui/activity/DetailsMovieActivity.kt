package br.com.andrecouto.nextel.themoviesdbapp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.andrecouto.nextel.themoviesdbapp.R
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.extensions.loadUrl
import br.com.andrecouto.nextel.themoviesdbapp.extensions.setupToolbar
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import br.com.andrecouto.nextel.themoviesdbapp.util.NetworkUtils
import kotlinx.android.synthetic.main.activity_details_movie.*
import kotlinx.android.synthetic.main.activity_details_movie_contents.*

class DetailsMovieActivity : AppCompatActivity() {
    val movie by lazy { intent.getParcelableExtra<Movie>("movie") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_movie)

        setupToolbar(R.id.toolbar, movie.title, true)

        initViews()
    }

    fun initViews() {
        appBarImg.loadUrl(Constants.BASE_URL_IMG_500 + movie.backdropPath, null, NetworkUtils.isNetworkAvailable(this))
        tDesc.setText(movie.overview)
    }
}
