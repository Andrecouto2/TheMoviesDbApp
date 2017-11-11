package br.com.andrecouto.nextel.themoviesdbapp.util

import android.util.Log
import br.com.andrecouto.nextel.themoviesdbapp.data.model.Movie
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatDateToString(date: Date?):String {
        var s:String = ""
        try
        {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            s = formatter.format(date)
        }
        catch (e:NumberFormatException) {
            Log.e("toString", "Erro na conversao da data para String! error:" + e)
        }
        return s
    }
}