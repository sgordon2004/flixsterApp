package com.example.flixster

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/now_playing")
    suspend fun nowPlaying(@Query("api_key") apiKey: String): MoviesResponse
}