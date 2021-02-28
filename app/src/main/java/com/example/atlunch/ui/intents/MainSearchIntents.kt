package com.example.atlunch.ui.intents

import com.example.atlunch.common.IViewIntent

sealed class MainSearchIntents : IViewIntent {
    data class GetSearchResults(val search : String) : MainSearchIntents()
    object ClearSearch : MainSearchIntents()
    object ShowMapIntent : MainSearchIntents()
    data class SelectRestaurantIntent(val position : Int): MainSearchIntents()
    object ShowListOverLay : MainSearchIntents()
    object RecenterMapView : MainSearchIntents()
    data class ScrollHorizontalList(val position : Int): MainSearchIntents()
}