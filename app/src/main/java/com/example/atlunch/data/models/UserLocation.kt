package com.example.atlunch.data.models

data class UserLocation(val longitude : String, val latitude : String, val radius : Int = DEFAULT_RADIUS){

    fun toPlacesRequestString() = "$latitude,$longitude"

    companion object{
        const val DEFAULT_RADIUS = 50
    }
}
