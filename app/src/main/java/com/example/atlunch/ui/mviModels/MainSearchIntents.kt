package com.example.atlunch.ui.mviModels

import com.example.atlunch.common.IViewIntent
import com.example.atlunch.data.models.UserLocation

sealed class MainSearchIntents : IViewIntent {
    data class GetSearchResults(val search : String?, val userLocation: UserLocation) : MainSearchIntents()
    object ShowMapIntent : MainSearchIntents()
    data class SelectRestaurantIntent(val position : Int): MainSearchIntents()
    object ShowListOverLay : MainSearchIntents()
    data class ScrollHorizontalList(val position : Int): MainSearchIntents()
}