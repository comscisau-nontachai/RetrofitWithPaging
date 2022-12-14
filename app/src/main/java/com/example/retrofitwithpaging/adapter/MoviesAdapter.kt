package com.example.retrofitwithpaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.retrofitwithpaging.R
import com.example.retrofitwithpaging.api.Constants
import com.example.retrofitwithpaging.databinding.ItemMoviesBinding
import com.example.retrofitwithpaging.response.MoviesListResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesAdapter @Inject constructor() : PagingDataAdapter<MoviesListResponse.Result,MoviesAdapter.ViewHolder>(diffCallback = differCallback) {
    private lateinit var binding : ItemMoviesBinding
    private lateinit var context : Context
    private var onItemClickListener : ((MoviesListResponse.Result) -> Unit)? = null

    companion object{
        val differCallback = object : DiffUtil.ItemCallback<MoviesListResponse.Result>() {
            override fun areItemsTheSame(
                oldItem: MoviesListResponse.Result,
                newItem: MoviesListResponse.Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MoviesListResponse.Result,
                newItem: MoviesListResponse.Result
            ) = oldItem == newItem

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMoviesBinding.inflate(inflater,parent,false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun bind(item : MoviesListResponse.Result){
            binding.apply {
                tvMovieName.text = item.title
                tvMovieDateRelease.text = item.releaseDate
                tvRate.text = item.voteAverage.toString()

                val moviePoster = Constants.POSTER_BASE_URL + item.posterPath
                imgMovie.load(moviePoster){
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                        .scale(Scale.FILL)
                }

                tvLang.text = item.originalLanguage

                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }


    fun setOnItemClickListener(listener : (MoviesListResponse.Result) -> Unit){
        onItemClickListener = listener
    }
}