package com.example.retrofitwithpaging.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.retrofitwithpaging.R
import com.example.retrofitwithpaging.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}