package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName
import java.io.Serial

@Parcelize
data class Movie (
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double
) : Parcelable

data class MoviesResponse(
    val results: List<Movie>
)

object ImageConfig {
    const val POSTER_BASE = "https://image.tmdb.org/t/p/w342"
    const val BACKDROP_BASE = "https://image.tmdb.org/t/p/w780"
}