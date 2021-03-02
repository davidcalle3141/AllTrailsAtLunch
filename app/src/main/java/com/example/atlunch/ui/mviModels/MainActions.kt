package com.example.atlunch.ui.mviModels

import com.example.atlunch.common.IViewAction
import com.google.android.gms.maps.model.LatLng

sealed class MainActions : IViewAction {
    data class SearchRestaurantsAction(val string: String?, val userLocation: LatLng?): MainActions()
    object ShowListAction: MainActions()
    object HideListAction: MainActions()
    data class HighlightPinAction(val position: Int, val scroll : Boolean = true): MainActions()
    data class HandleFavoriteAction(val place_id : String, val position: Int) : MainActions()
}
