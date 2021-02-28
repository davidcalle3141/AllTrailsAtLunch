package com.example.atlunch.data.repositories


import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.NetworkResponse
import com.example.atlunch.data.models.UserLocation
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRestaurantRepo {
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id : String)
    suspend fun getFavorites(): List<Favorite>
    suspend fun getRestaurants(userLocation: UserLocation, query : String?): Flow<ApiResponseWrapper<Response<NetworkResponse>>>
}