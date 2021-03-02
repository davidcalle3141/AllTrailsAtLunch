package com.example.atlunch.data.repositories

import android.util.Log
import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.api.PlacesApiService
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.Restaurant
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RestaurantRepo(
    private val favoriteDao: FavoriteDao,
    private val placesApi: PlacesApiService
) : IRestaurantRepo {
    private val TAG = RestaurantRepo::class.java.simpleName
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

    fun isFavorite(id: String) = favoritesSet.contains(id)

    suspend fun toggleFavorite(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (favoritesSet.contains(id)) removeFavorite(id)
                else addFavorite(id)
                return@withContext true
            } catch (e: Exception) {
                Log.e(RestaurantRepo::class.java.simpleName, e.message, e)
            }
            return@withContext false
        }

    }

    override suspend fun addFavorite(id: String) {
        favoritesSet.add(id)
        favoriteDao.addFavorite(Favorite(id))
        Log.d(TAG, "writing new favorite to db")
    }

    override suspend fun removeFavorite(id: String) {
        favoritesSet.remove(id)
        favoriteDao.deleteFavorite(id)
        Log.d(TAG, "removing favorite from dp")

    }

    override suspend fun getFavorites() = favoriteDao.getAll()


    override suspend fun getRestaurants(
        userLocation: LatLng,
        query: String?,
    ): Flow<ApiResponseWrapper<List<Restaurant>>> = flow {
        emit(ApiResponseWrapper.Loading)
        try {
            Log.d(TAG, "making API call")
            val restaurants = placesApi.getRestaurantsFromPlaces(
                "${userLocation.latitude},${userLocation.longitude}",
                query ?: ""
            ).body()?.results ?: listOf()

            restaurants.forEach {
                it.setFavorite(isFavorite(it.place_id))
            }

            emit(ApiResponseWrapper.Success(restaurants))


        } catch (e: Exception) {
            emit(ApiResponseWrapper.Error(e))
            Log.e(TAG,"Error retrieving places from API", e)

        }


    }


}