package br.com.andrecouto.nextel.themoviesdbapp.extensions

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?, progress: ProgressBar? = null, isConnected: Boolean) {
    if (url == null || url.trim().isEmpty()) {
        setImageBitmap(null)
        return
    }
    if (progress == null) {
        Picasso.with(context).load(url).fit().into(this)
    } else {
        progress.visibility = View.VISIBLE
        if (isConnected) {
            Picasso.with(context).load(url).fit().into(this,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            // Download OK
                            progress.visibility = View.GONE
                        }

                        override fun onError() {
                            progress.visibility = View.GONE
                        }
                    })
        } else {
            Picasso.with(context).load(url).networkPolicy(NetworkPolicy.OFFLINE).fit().into(this,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            // Download OK
                            progress.visibility = View.GONE
                        }

                        override fun onError() {
                            progress.visibility = View.GONE
                        }
                    })
        }

    }
}
