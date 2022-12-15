package com.example.retrofitwithpaging.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitwithpaging.adapter.LoadMoreAdapter
import com.example.retrofitwithpaging.adapter.MoviesAdapter
import com.example.retrofitwithpaging.databinding.FragmentMoviesBinding
import com.example.retrofitwithpaging.repository.ApiRepository
import com.example.retrofitwithpaging.response.MoviesListResponse
import com.example.retrofitwithpaging.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(){

    private lateinit var binding : FragmentMoviesBinding
    @Inject
    lateinit var movieAdapter : MoviesAdapter

    private val viewModel : MoviesViewModel by viewModels()

    val TAG = "movie fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            lifecycleScope.launchWhenCreated {
                viewModel.moviesList.collect {
                    movieAdapter.submitData(it)
                }
            }

            movieAdapter.setOnItemClickListener {
                val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                findNavController().navigate(direction)
            }

            lifecycleScope.launchWhenCreated {
                movieAdapter.loadStateFlow.collect{
                    val state = it.refresh
                    prgBarMovies.isVisible = state is LoadState.Loading
                }
            }


            rlMovies.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = movieAdapter
            }

            rlMovies.adapter=movieAdapter.withLoadStateFooter(
                LoadMoreAdapter{
                    movieAdapter.retry()
                }
            )

        }

    }


}