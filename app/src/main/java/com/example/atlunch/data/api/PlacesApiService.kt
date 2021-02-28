package com.example.atlunch.data.api

import com.example.atlunch.BuildConfig
import com.example.atlunch.MainApplication
import com.example.atlunch.R
import com.example.atlunch.data.models.Location
import com.example.atlunch.data.models.Restaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {

    @GET("maps/api/place/textsearch/json")
    suspend fun getRestaurantsWithQuery(
        @Query("query") query: String?,
        @Query("longitude") long: String,
        @Query("latitude") lat: String,
        @Query("radius") radius: Int
    ): Response<List<Restaurant>>

    @GET("maps/api/place/nearbysearch/json")
    suspend fun getRestaurantsNearby(
        @Query("longitude") long: String,
        @Query("latitude") lat: String,
        @Query("radius") radius: Int
    ): Response<List<Restaurant>>

}