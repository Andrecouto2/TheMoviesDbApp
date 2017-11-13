package br.com.andrecouto.nextel.themoviesdbapp.adapter

import android.content.Context
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.andrecouto.nextel.themoviesdbapp.R
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import br.com.andrecouto.nextel.themoviesdbapp.extensions.loadUrl
import br.com.andrecouto.nextel.themoviesdbapp.util.Constants
import br.com.andrecouto.nextel.themoviesdbapp.util.NetworkUtils
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class MovieAdapter(val context: Context, val movies: ArrayList<Movie>, val onClick: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MoviesViewHolder>() {

    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var errorMsg: String = ""

    override fun getItemCount(): Int {
         return movies.size
    }

    val isEmpty: Boolean
        get() {
            return movies.size == 0
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        var layout = -1
        when (viewType) {
            ITEM -> {
                layout = R.layout.item_movie
            }
            LOADING -> {
                layout = R.layout.item_progress
            }
        }
        val v = LayoutInflater
                .from(context)
                .inflate(layout, parent, false)
        return MoviesViewHolder(v)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie: Movie = movies.get(position) // Movie
        when (getItemViewType(position)) {
            ITEM -> {
                val view = holder.itemView
                with(view) {
                    tMovieVoteLanguage.setText(Html.fromHtml(context.getString(R.string.vote_and_language, movie.voteAverage!!.toString(), movie.originalLanguage)))
                    tMovieTitle.setText(movie.title)
                    tMovieDesc.setText(movie.overview)
                    moviePoster.loadUrl(Constants.BASE_URL_IMG_150 + movie.posterPath, movieProgress, NetworkUtils.isNetworkAvailable(context))
                    setOnClickListener { onClick(movie) }
                }
            }
            LOADING -> {
                val view = holder.itemView
                with(view) {
                    if (retryPageLoad) {

                        loadMoreErrorLayout.visibility = View.VISIBLE
                        loadMoreProgress.visibility = View.GONE
                        tloadMoreError.setText(if (errorMsg != null)
                            errorMsg
                        else
                            context.getString(R.string.error_msg_unknown))

                    } else {
                        loadMoreErrorLayout.visibility = View.GONE
                        loadMoreProgress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if ((position == movies.size - 1 && isLoadingAdded)) LOADING else ITEM

    }

    fun add(r:Movie) {
        movies.add(r)
        notifyItemInserted(movies.size - 1)
    }

    fun addAll(moveResults:List<Movie>) {
        for (result in moveResults)
        {
            add(result)
        }
        notifyDataSetChanged()
    }

    fun remove(r:Movie) {
        val position = movies.indexOf(r)
        if (position > -1)
        {
            movies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0)
        {
            remove(getItem(0))
        }
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        if (NetworkUtils.isNetworkAvailable(context)) {
            isLoadingAdded = true
            add(Movie())
        }
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        for (movie in movies) {
            if (movie.title!!.isEmpty()) {
                movies.removeAt(movies.indexOf(movie))
                notifyItemRemoved(movies.indexOf(movie))
                break;
            }
        }
    }

    fun getItem(position:Int):Movie {
        return movies.get(position)
    }

    fun showRetry(show:Boolean, @Nullable errorMsg:String) {
        retryPageLoad = show
        notifyItemChanged(movies.size - 1)
        if (errorMsg != null) this.errorMsg = errorMsg
    }

    companion object {
        private val ITEM = 0
        private val LOADING = 1
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}