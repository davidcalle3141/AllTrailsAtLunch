package com.example.atlunch.data.repositories

import androidx.annotation.WorkerThread
import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.api.PlacesApiService
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.Location
import com.example.atlunch.data.models.Restaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class RestaurantRepo(private val favoriteDao: FavoriteDao, private val placesApi: PlacesApiService) : IRestaurantRepo {
    private val favoritesSet = hashSetOf<String>()
    private var initFavorites: Boolean = false


    suspend fun initFavorites(){
        if(!initFavorites){
            withContext(Dispatchers.IO){
                favoritesListToSet(getFavorites())
                initFavorites = true
            }
        }

    }

    private fun favoritesListToSet(list : List<Favorite>){
        list.forEach { favoritesSet.add(it.place_id) }
    }

    override suspend fun addFavorite(id : String) {
        favoritesSet.add(id)
        favoriteDao.addFavorite(Favorite(id))
    }
    override suspend fun removeFavorite(id: String){
        favoritesSet.remove(id)
        favoriteDao.deleteFavorite(id)
    }

    override suspend fun getFavorites() = favoriteDao.getAll()



    override suspend fun getRestaurants(
        location: Location,
        query: String?,
    ): Flow<ApiResponseWrapper<List<Restaurant>>> = flow {
        emit(ApiResponseWrapper.Loading)
        try {
            if(query == null) emit(ApiResponseWrapper.Success(searchNearby(location).body()?: listOf()))
            else emit(ApiResponseWrapper.Success(searchByQuery(query, location).body()?: listOf()))
        }catch (e : Exception){
            emit(ApiResponseWrapper.Error(e))
        }


    }

    private suspend fun searchNearby(location: Location): Response<List<Restaurant>> {
        return placesApi.getRestaurantsNearby(location.longitude, location.latitude, location.radius)
    }

    private suspend fun searchByQuery(query: String, location: Location): Response<List<Restaurant>> {
        return placesApi.getRestaurantsWithQuery(query,location.longitude,location.latitude,location.radius)
    }



}