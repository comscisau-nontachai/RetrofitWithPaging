package com.example.retrofitwithpaging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.retrofitwithpaging.R
import com.example.retrofitwithpaging.api.Constants
import com.example.retrofitwithpaging.databinding.FragmentMovieDetailsBinding
import com.example.retrofitwithpaging.repository.ApiRepository
import com.example.retrofitwithpaging.response.MovieDetailsResponse
import com.example.retrofitwithpaging.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding : FragmentMovieDetailsBinding
    private val arg : MovieDetailsFragmentArgs by navArgs()
    private val viewModel : MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieId = arg.movieId
        if(movieId > 0){
            viewModel.loadMovieDetails(movieId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.detailsMovies.observe(viewLifecycleOwner){ body ->
                val posterUrl = Constants.POSTER_BASE_URL+body.posterPath
                imgMovie.load(posterUrl){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    scale(Scale.FILL)
                }
                tvMovieTitle.text = body.title
                tvMovieTagLine.text = body.tagline
                tvMovieDateRelease.text = body.releaseDate
                tvMovieRating.text = body.voteAverage.toString()
                tvMovieRuntime.text = body.runtime.toString()
                tvMovieBudget.text = body.budget.toString()
                tvMovieRevenue.text = body.revenue.toString()
                tvMovieOverview.text = body.overview
            }

            viewModel.loading.observe(viewLifecycleOwner){
                prgBarMovies.isVisible = it
            }

        }
    }


}