package com.example.atlunch.data.repositories

import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.api.PlacesApiService
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.NetworkResponse
import com.example.atlunch.data.models.UserLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class RestaurantRepo(
    private val favoriteDao: FavoriteDao,
    private val placesApi: PlacesApiService
) : IRestaurantRepo {
    private val favoritesSet = hashSetOf<String>()
    private var initFavorites: Boolean = false


    suspend fun initFavorites() {
        if (!initFavorites) {
            withContext(Dispatchers.IO) {
                favoritesListToSet(getFavorites())
                initFavorites = true
            }
        }
    }

    private fun favoritesListToSet(list: List<Favorite>) {
        list.forEach { favoritesSet.add(it.place_id) }
    }

    override suspend fun addFavorite(id: String) {
        favoritesSet.add(id)
        favoriteDao.addFavorite(Favorite(id))
    }

    override suspend fun removeFavorite(id: String) {
        favoritesSet.remove(id)
        favoriteDao.deleteFavorite(id)
    }

    override suspend fun getFavorites() = favoriteDao.getAll()


    override suspend fun getRestaurants(
        userLocation: UserLocation,
        query: String?,
    ): Flow<ApiResponseWrapper<Response<NetworkResponse>>> = flow {
        emit(ApiResponseWrapper.Loading)
        try {
            emit(
                ApiResponseWrapper.Success(
                    placesApi.getRestaurantsFromPlaces(
                        userLocation.toPlacesRequestString(),
                        query ?: ""
                    )
                )
            )
        } catch (e: Exception) {
            emit(ApiResponseWrapper.Error(e))
        }


    }


}