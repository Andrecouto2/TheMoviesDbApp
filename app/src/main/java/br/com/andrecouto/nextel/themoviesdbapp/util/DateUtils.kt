package br.com.andrecouto.nextel.themoviesdbapp.util

import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie

object DateUtils {

    fun formatYearLabel(result: Movie): String {
        return (result.releaseDate.toString().substring(result.releaseDate.toString().count()-4)
                + " | "
                + result.originalLanguage!!.toUpperCase())
    }
}