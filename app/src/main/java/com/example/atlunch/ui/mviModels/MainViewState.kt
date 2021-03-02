package com.example.atlunch.ui.mviModels

import com.example.atlunch.common.IListViewState
import com.example.atlunch.data.models.Restaurant

sealed class MainViewState : IListViewState<Restaurant> {
    data class MapState(
        override val list: List<Restaurant>,
        val selected: Int? = null,
        val scrollToSelected: Boolean? = false
    ): MainViewState()
    data class ShowListState(override val list : List<Restaurant> ): MainViewState()
    data class SearchResultState(override val list: List<Restaurant>): MainViewState()
    data class LoadingState(override val list: List<Restaurant>) : MainViewState()
}
