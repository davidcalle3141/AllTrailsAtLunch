package com.example.atlunch.ui.mviModels

import com.example.atlunch.common.IViewAction
import com.example.atlunch.data.models.UserLocation

sealed class MainSearchActions : IViewAction {
    data class SearchRestaurants(val string: String?, val userLocation: UserLocation): MainSearchActions()
    object ShowListOverlay: MainSearchActions()
    object HideListOverLay: MainSearchActions()
    data class HighlightMapPin(val position: Int): MainSearchActions()
}
