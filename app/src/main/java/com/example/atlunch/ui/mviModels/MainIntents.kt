package com.example.atlunch.ui.mviModels

import com.example.atlunch.common.IViewIntent
import com.google.android.gms.maps.model.LatLng

sealed class MainIntents : IViewIntent {
    data class GetPlacesIntent(val search: String?, val userLocation: LatLng?) : MainIntents()
    object ShowMapIntent : MainIntents()
    data class SelectRestaurantIntent(val position : Int): MainIntents()
    object ShowListIntent : MainIntents()
    data class ScrollHorizontalListIntent(val position : Int): MainIntents()
    data class FavoriteClickedIntent(val place_id : String, val position: Int): MainIntents()
}