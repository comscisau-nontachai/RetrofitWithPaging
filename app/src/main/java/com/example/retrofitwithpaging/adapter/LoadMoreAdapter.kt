package com.example.retrofitwithpaging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitwithpaging.databinding.ItemLoadMoreBinding

class LoadMoreAdapter(private val retry : () -> Unit) : LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {
    private lateinit var binding: ItemLoadMoreBinding

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        binding = ItemLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(retry)
    }

    inner class ViewHolder(private val retry : () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnLoadMoreRetry.setOnClickListener {
                retry()
            }
        }
        fun setData(state: LoadState) {
            binding.apply {
                prgBarLoadMore.isVisible = state is LoadState.Loading
                tvLoadMore.isVisible = state is LoadState.Error
                btnLoadMoreRetry.isVisible = state is LoadState.Error
            }
        }
    }
}