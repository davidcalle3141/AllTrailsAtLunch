package com.example.atlunch.data.models

data class Restaurant(
    val name: String,
    val plusCode: String,
    val rating: Double,
    val price_level: Int,
    val photos: List<Photo>,
    val opening_hours: OpenHours
)

data class Photo(val photo_reference: String)
data class OpenHours(val open_now: Boolean)