package com.example.retrofitwithpaging.api

import com.example.retrofitwithpaging.response.MovieDetailsResponse
import com.example.retrofitwithpaging.response.MoviesListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page : Int) : Call<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Call<MovieDetailsResponse>
}