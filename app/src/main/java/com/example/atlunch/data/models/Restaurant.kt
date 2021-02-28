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
        const val PLACE_ID = "place_id"
        const val NAME = "name"
        const val PLUS_CODE = "plusCode"
        const val RATING = "rating"
        const val PRICE = "price_level"
        const val PHOTOS = "photos"
        const val OPENNOW = "opening_hours/open_now"
        const val TYPE = "restaurant"

        val DefaultFieldsList = listOf(PLACE_ID, NAME, PLUS_CODE, RATING, PRICE, PHOTOS, OPENNOW,
            TYPE)
    }
}

data class Photo(val photo_reference: String)
data class OpenHours(val open_now: Boolean)