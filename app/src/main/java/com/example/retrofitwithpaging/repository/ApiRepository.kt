package com.example.retrofitwithpaging.repository

import com.example.retrofitwithpaging.api.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

//@ActivityScoped
class ApiRepository @Inject constructor(
    private val api : ApiServices
){

    suspend fun getPopularMovies(page : Int) = api.getPopularMoviesList(page)

    suspend fun getMovieDetails(movieId : Int) = api.getMovieDetails(movieId)
}