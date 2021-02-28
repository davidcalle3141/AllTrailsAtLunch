package com.example.atlunch.ui.viewState

import com.example.atlunch.common.IViewState
import com.example.atlunch.data.models.Restaurant

sealed class MainSearchViewState : IViewState {
    data class MapState(val restaurants: List<Restaurant>, val selected : Int? = null): MainSearchViewState()
    data class ListState(val restaurants: List<Restaurant>): MainSearchViewState()
    object Loading : MainSearchViewState()
}
