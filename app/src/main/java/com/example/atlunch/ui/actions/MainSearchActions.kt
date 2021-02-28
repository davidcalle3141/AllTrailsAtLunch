package com.example.atlunch.ui.actions

import com.example.atlunch.common.IViewAction
import com.example.atlunch.data.models.Location

sealed class MainSearchActions : IViewAction {
    data class SearchRestaurants(val string: String?, val location: Location): MainSearchActions()
    object ShowListOverlay: MainSearchActions()
    object HideListOverLay: MainSearchActions()
    data class HighlightMapPin(val position: Int): MainSearchActions()
}
