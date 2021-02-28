package com.example.atlunch.data.api

import com.example.atlunch.data.models.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {

    @GET("maps/api/place/textsearch/json")
    suspend fun getRestaurantsFromPlaces(
        @Query("location") long: String,
        @Query("query") query: String,
        ): Response<NetworkResponse>


}