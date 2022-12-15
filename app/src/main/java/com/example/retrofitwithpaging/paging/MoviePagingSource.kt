package com.example.retrofitwithpaging.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofitwithpaging.repository.ApiRepository
import com.example.retrofitwithpaging.response.MoviesListResponse
import retrofit2.HttpException

class MoviePagingSource(
    private val repository: ApiRepository
) : PagingSource<Int,MoviesListResponse.Result>(){
    override fun getRefreshKey(state: PagingState<Int, MoviesListResponse.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getPopularMovies(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<MoviesListResponse.Result>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if(currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }catch (exception : HttpException){
            LoadResult.Error(exception)
        }
    }
}