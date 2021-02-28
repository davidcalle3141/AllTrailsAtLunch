package com.example.atlunch.data.repositories

import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.api.PlacesApiService
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.Restaurant
import kotlinx.coroutines.flow.Flow

class RestaurantRepo(private val favoriteDao: FavoriteDao, private val placesApi: PlacesApiService) : IRestaurantRepo {
    private val favoritesSet = hashSetOf<String>()


    init {
        favoritesListToSet(favoriteDao.getAll())
    }


    private fun favoritesListToSet(list : List<Favorite>){
        list.forEach { favoritesSet.add(it.place_id) }
    }

    override fun addFavorite(id : String) {
        favoritesSet.add(id)

        favoriteDao.addFavorite(Favorite(id))
    }
    override fun removeFavorite(id: String){
        favoritesSet.remove(id)
        favoriteDao.deleteFavorite(id)
    }

    override fun getRestaurants(
        location: com.example.atlunch.data.models.Location,
        query: String?
    ): Flow<ApiResponseWrapper<List<Restaurant>>> {
        TODO("Not yet implemented")
    }

}