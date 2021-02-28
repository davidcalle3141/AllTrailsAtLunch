package com.example.atlunch.data.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.atlunch.data.models.Favorite

interface FavoriteDao {
    @Insert
    fun addFavorite(favorite: Favorite)
    @Query("DELETE FROM favorite WHERE place_id = place_id  ")
    fun deleteFavorite(place_id : String)
    @Query("SELECT * From favorite")
    fun getAll(): List<Favorite>

}