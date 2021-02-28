package com.example.atlunch.data.repositories

import android.location.Location
import com.example.atlunch.data.models.Restaurant
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepo {
    fun addFavorite(id: String)
    fun removeFavorite(id : String)
    fun getRestaurants(location: Location, query : String): Flow<Result<List<Restaurant>>>
}