package com.example.atlunch.data.models

data class Restaurant(
    val place_id: String,
    val name: String,
    val plusCode: String,
    val rating: Double,
    val price_level: Int,
    val photos: List<Photo>,
    val opening_hours: OpenHours,
    val type : String
){
    companion object{
        const val PlaceID = "place_id"
        const val Name = "name"
        const val plusCode = "plusCode"
        const val Rating = "rating"
        const val Price = "price_level"
        const val Photos = "photos"
        const val OpenNow = "opening_hours/open_now"
        const val Type = "restaurant"
    }
}

data class Photo(val photo_reference: String)
data class OpenHours(val open_now: Boolean)