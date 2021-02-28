package com.example.atlunch.data.models

data class NetworkResponse(
    val results : List<Restaurant>
){
    companion object{
        const val TYPE = "restaurant"
        const val RANKBY = "distance"
    }

}
