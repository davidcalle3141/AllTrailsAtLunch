package com.example.atlunch.ui.actions

import com.example.atlunch.data.models.Location

sealed class SearchActions{
    data class SearchRestaurants(val string: String): SearchActions()
    data class UpdateSearchWindow(val location: Location) : SearchActions()
    object ShowListOverlay: SearchActions()
    object HideListOverLay: SearchActions()
    data class HideListOverLayWithSelection(val position : Int): SearchActions()
}
