package com.example.retrofitwithpaging.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitwithpaging.adapter.MoviesAdapter
import com.example.retrofitwithpaging.databinding.FragmentMoviesBinding
import com.example.retrofitwithpaging.repository.ApiRepository
import com.example.retrofitwithpaging.response.MoviesListResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(){

    private lateinit var binding : FragmentMoviesBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var movieAdapter : MoviesAdapter

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
            prgBarMovies.visibility = View.VISIBLE

            apiRepository.getPopularMovies(1).enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    when(response.code()){
                       in 200..299 ->{
                           prgBarMovies.visibility = View.GONE
                           response.body()?.let { body ->
                               body.results.let { item ->
                                    if(item.isNotEmpty()){
                                        movieAdapter.differ.submitList(item)

                                        rlMovies.apply {
                                            layoutManager = LinearLayoutManager(requireContext())
                                            adapter = movieAdapter
                                        }

                                        movieAdapter.setOnItemClickListener {
                                            val direction = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it.id)
                                            findNavController().navigate(direction)
                                        }
                                    }
                               }
                           }
                       }
                       in 300..399 -> Toast.makeText(requireContext(), "Redirection message ${response.code()}", Toast.LENGTH_SHORT).show()
                       in 400..499 -> Toast.makeText(requireContext(), "Client error ${response.code()}", Toast.LENGTH_SHORT).show()
                       in 500..599 -> Toast.makeText(requireContext(), "Server  error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    prgBarMovies.visibility = View.GONE
                    Log.d("LOGD", "onFailure: ${t.message}")
                }

            })
        }
    }


}