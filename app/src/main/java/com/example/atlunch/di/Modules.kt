package com.example.atlunch.di

import androidx.room.Room
import com.example.atlunch.data.database.ApplicationDB
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.models.Location
import com.example.atlunch.data.repositories.RestaurantRepo
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


    val appModule = module {
        single { Room.databaseBuilder(androidApplication(), ApplicationDB::class.java, "application-db") }
        single<FavoriteDao>{get<ApplicationDB>().favoritesDao()}
        single<RestaurantRepo>{ RestaurantRepo(get()) }
        viewModel { SearchViewModel(get()) }
    }
