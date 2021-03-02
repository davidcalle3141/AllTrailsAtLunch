package com.example.atlunch.ui.adapters

import com.example.atlunch.R
import com.example.atlunch.data.models.Restaurant
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
/**
 * class to handle GoogleMap Markers
 * **/
class MarkerAdapter(private val map : GoogleMap) {
    private var markerList : ArrayList<Marker> = ArrayList()
    private var bounds = LatLngBounds.Builder()
    private var lastClickedMarker : Marker? = null
    fun getSize() = markerList.size
    fun setData(restaurantList: List<Restaurant>){
       clear()
        restaurantList.forEachIndexed { index, restaurant ->
            val latLng  = LatLng(restaurant.geometry.location.lat, restaurant.geometry.location.lng)
            val marker = map.addMarker(
                MarkerOptions().position(latLng).icon(
                    BitmapDescriptorFactory.fromResource(
                        R.drawable.grey_marker
                    )
                )
            )
            marker.tag = index
            markerList.add(marker)
            bounds.include(latLng)
        }

    }




    fun setMarkerSelected(position : Int){
        lastClickedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.grey_marker))
        getMarker(position)?.let {
            lastClickedMarker = it
            it.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_marker))

        }
    }

    fun getMarker(position: Int): Marker?{
        if (position in 0 until markerList.size) return markerList[position]
        return null
    }
    fun getBoundsBuilder() = bounds



    fun clear(){
        bounds = LatLngBounds.builder()
        lastClickedMarker = null
        markerList.forEach {
            it.remove() }
        markerList.clear()
    }


}