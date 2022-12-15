package com.example.retrofitwithpaging.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.retrofitwithpaging.paging.MoviePagingSource
import com.example.retrofitwithpaging.repository.ApiRepository
import com.example.retrofitwithpaging.response.MovieDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    val moviesList = Pager(PagingConfig(1)) {
        MoviePagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    val detailsMovies = MutableLiveData<MovieDetailsResponse>()
    val loading = MutableLiveData<Boolean>()

    fun loadMovieDetails(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.getMovieDetails(id)
        if (response.isSuccessful) {
            detailsMovies.postValue(response.body())
        }
        loading.postValue(false)
    }


}