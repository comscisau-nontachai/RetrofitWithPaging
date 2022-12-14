package com.example.retrofitwithpaging.repository

import com.example.retrofitwithpaging.api.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val api : ApiServices
){

    fun getPopularMovies(page : Int) = api.getPopularMoviesList(page)

    fun getMovieDetails(movieId : Int) = api.getMovieDetails(movieId)
}