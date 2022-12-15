package com.example.retrofitwithpaging.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.example.retrofitwithpaging.R
import com.example.retrofitwithpaging.api.Constants
import com.example.retrofitwithpaging.databinding.FragmentMovieDetailsBinding
import com.example.retrofitwithpaging.repository.ApiRepository
import com.example.retrofitwithpaging.response.MovieDetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    @Inject
    lateinit var api : ApiRepository
    private lateinit var binding : FragmentMovieDetailsBinding
    private val arg : MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arg.movieId
        binding.apply {

            api.getMovieDetails(movieId).enqueue(object : Callback<MovieDetailsResponse> {
                override fun onResponse(
                    call: Call<MovieDetailsResponse>,
                    response: Response<MovieDetailsResponse>
                ) {
                    when(response.code()){
                        in 200..299 -> {
                            response.body()?.let { body ->
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
                        }
                        in 300..399 -> Toast.makeText(requireContext(), "error redirection", Toast.LENGTH_SHORT).show()
                        in 400..499 -> Toast.makeText(requireContext(), "error client", Toast.LENGTH_SHORT).show()
                        in 500..599 -> Toast.makeText(requireContext(), "error server", Toast.LENGTH_SHORT).show()
                    }
                    prgBarMovies.visibility = View.GONE
                }

                override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


}