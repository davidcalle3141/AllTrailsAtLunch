package com.example.atlunch.ui.intents

import com.example.atlunch.common.IViewIntent
import com.example.atlunch.data.models.Location

sealed class MainSearchIntents : IViewIntent {
    data class GetSearchResults(val search : String?, val location: Location) : MainSearchIntents()
    object ShowMapIntent : MainSearchIntents()
    data class SelectRestaurantIntent(val position : Int): MainSearchIntents()
    object ShowListOverLay : MainSearchIntents()
    data class ScrollHorizontalList(val position : Int): MainSearchIntents()
}