package com.example.atlunch.di

import android.app.Application
import androidx.room.Room
import com.example.atlunch.data.database.ApplicationDB
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.repositories.RestaurantRepo
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


    val appModule = module {
        single<RestaurantRepo>{ RestaurantRepo(get(), get()) }
        viewModel {(locationEnabled: Boolean) -> SearchViewModel(get(), locationEnabled) }
    }

    val dataBaseModule = module{
        fun provideDatabase(application: Application): ApplicationDB {
            return Room.databaseBuilder(application, ApplicationDB::class.java, "favorites")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideFavoritesDao(database: ApplicationDB): FavoriteDao {
            return  database.favoritesDao()
        }

        single { provideDatabase(androidApplication()) }
        single { provideFavoritesDao(get()) }
    }