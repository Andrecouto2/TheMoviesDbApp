package br.com.andrecouto.nextel.themoviesdbapp.util

import android.content.Context
import br.com.andrecouto.nextel.themoviesdbapp.R
import br.com.andrecouto.nextel.themoviesdbapp.data.dao.DatabaseManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object SearchUtils {

      fun getResultSearchString(context: Context, size: Int) : String {
          if (size == 0) {
              return context.getString(R.string.label_find_movies_singular, 0)
          } else if (size == 1) {
              return context.getString(R.string.label_find_movies_singular, 1)
          } else if (size < 10) {
              return context.getString(R.string.label_find_movies, "0"+size)
          } else {
              return context.getString(R.string.label_find_movies, size)
          }
      }
}