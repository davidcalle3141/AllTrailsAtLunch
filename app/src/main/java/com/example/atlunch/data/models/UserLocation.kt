package com.example.atlunch.data.models

import com.google.android.gms.maps.model.LatLng

data class UserLocation(val longitude : Double, val latitude : Double){
    fun getLatLng() = LatLng(latitude,longitude)
    fun toPlacesRequestString() = "$latitude,$longitude"

}
