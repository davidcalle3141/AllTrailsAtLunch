package com.example.atlunch.data.repositories


import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.Restaurant
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface IRestaurantRepo {
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id : String)
    suspend fun getFavorites(): List<Favorite>
    suspend fun getRestaurants(userLocation: LatLng, query : String?): Flow<ApiResponseWrapper<List<Restaurant>>>
}