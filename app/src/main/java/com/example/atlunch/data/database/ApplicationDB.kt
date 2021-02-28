package com.example.atlunch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atlunch.data.models.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class ApplicationDB : RoomDatabase() {
    abstract fun favoritesDao(): FavoriteDao
}