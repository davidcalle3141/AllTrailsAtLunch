package com.example.atlunch.data.models

data class Location(val longitude : String, val latitude : String, val radius : Int = DEFAULT_RADIUS){
    companion object{
        const val DEFAULT_RADIUS = 50
    }
}
