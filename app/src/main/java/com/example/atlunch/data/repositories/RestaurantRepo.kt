package com.example.atlunch.data.repositories

import android.location.Location
import com.example.atlunch.data.database.FavoriteDao
import com.example.atlunch.data.models.Favorite
import com.example.atlunch.data.models.Restaurant
import kotlinx.coroutines.flow.Flow

class RestaurantRepo(private val favoriteDao: FavoriteDao) : IRestaurantRepo {
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

    override fun getRestaurants(location: Location, query: String): Flow<Result<List<Restaurant>>> {
        TODO("Not yet implemented")
    }

}