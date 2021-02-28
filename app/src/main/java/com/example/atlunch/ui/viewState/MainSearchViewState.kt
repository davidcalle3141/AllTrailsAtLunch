package com.example.atlunch.ui.viewState

import com.example.atlunch.data.models.Restaurant
import com.example.atlunch.ui.actions.SearchActions

sealed class MainSearchViewState{
    data class MapState(val restaurants: List<Restaurant>, val selected : Int? = null): MainSearchViewState()
    data class ListState(val restaurants: List<Restaurant>): MainSearchViewState()
    object Loading : MainSearchViewState()
}
