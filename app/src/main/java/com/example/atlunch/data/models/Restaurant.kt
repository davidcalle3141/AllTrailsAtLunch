package com.example.atlunch.data.models

data class Restaurant(
    val place_id: String,
    val name: String,
    val plusCode: String?,
    val rating: Double?,
    val price_level: Int?,
    val photos: List<Photo>?,
    val opening_hours: OpenHours?,
    val type : String?,
    val geometry: Geometry,
    private var isFavorite: Boolean?,
    val coverPhotoUrl : String?
){
    fun getPriceLevel(): String {
        if(price_level == null) return ""
        var price = ""
        for (i in 0 until price_level){
            price+= "$"
        }
        return price
    }
    fun setFavorite(value : Boolean){
        if(isFavorite == null) isFavorite = value
    }
    fun getFavorite() = isFavorite?: false
}

data class Geometry (

    val location: Location
)

data class Location (

    val lat : Double,
    val lng : Double
)
data class Photo(val photo_reference: String)
data class OpenHours(val open_now: Boolean?)