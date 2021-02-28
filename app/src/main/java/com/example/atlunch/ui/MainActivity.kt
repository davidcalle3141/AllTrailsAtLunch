package com.example.atlunch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.atlunch.R
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val viewModel  by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}