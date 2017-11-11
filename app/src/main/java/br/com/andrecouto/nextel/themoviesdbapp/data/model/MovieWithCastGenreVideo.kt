package br.com.andrecouto.nextel.themoviesdbapp.data.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation


class MovieWithCastGenreVideo {

    @Embedded
    lateinit var movie: Movie

    @Relation(parentColumn = "id", entityColumn = "movieId", entity = Video::class)
    lateinit var videos: List<Video>

    @Relation(parentColumn = "id", entityColumn = "movieId", entity = Cast::class)
    lateinit var casts: List<Cast>

    @Relation(parentColumn = "id", entityColumn = "movieId", entity = Cast::class)
    lateinit var genres: List<Genre>
}