package com.example.atlunch.ui.viewmodel

import com.example.atlunch.data.repositories.RestaurantRepo
import com.example.atlunch.ui.mviModels.MainSearchViewState
import com.example.atlunch.common.MVIBaseViewModel
import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.data.models.Location
import com.example.atlunch.data.models.Restaurant
import com.example.atlunch.ui.mviModels.MainSearchActions
import com.example.atlunch.ui.mviModels.MainSearchIntents
import kotlinx.coroutines.flow.collect

class SearchViewModel(private val repo: RestaurantRepo) :
    MVIBaseViewModel<MainSearchIntents, MainSearchActions, MainSearchViewState>() {


    init {
        updateState(MainSearchViewState.MapState(listOf(),null))
        launchTask { repo.initFavorites() }
    }

    override fun intentToAction(intent: MainSearchIntents): MainSearchActions {
        return when (intent) {
            is MainSearchIntents.GetSearchResults -> MainSearchActions.SearchRestaurants(
                intent.search,
                intent.location
            )
            is MainSearchIntents.ScrollHorizontalList -> MainSearchActions.HighlightMapPin(intent.position)
            is MainSearchIntents.SelectRestaurantIntent -> MainSearchActions.HighlightMapPin(intent.position)
            MainSearchIntents.ShowListOverLay -> MainSearchActions.ShowListOverlay
            MainSearchIntents.ShowMapIntent -> MainSearchActions.HideListOverLay
        }
    }

    override fun handleAction(action: MainSearchActions) {
        val currentState = getState()
        when (action) {
            is MainSearchActions.HighlightMapPin -> onHighlightMapPin(action.position, currentState)
            is MainSearchActions.SearchRestaurants -> onSearchRestaurants(
                action.string,
                action.location,
                currentState
            )
            MainSearchActions.HideListOverLay -> hideShowListOverlay(currentState)
            MainSearchActions.ShowListOverlay -> hideShowListOverlay(currentState)
        }
    }

    private fun onHighlightMapPin(position: Int, currentState: MainSearchViewState) {
        if (currentState is MainSearchViewState.MapState) {
            updateState(currentState.copy(selected = position))
        } else if (currentState is MainSearchViewState.ListState) {
            updateState(MainSearchViewState.MapState(currentState.restaurants, position))
        }
    }

    private fun onSearchRestaurants(
        query: String?,
        location: Location,
        currentState: MainSearchViewState
    ) {
        launchTask {
            repo.getRestaurants(location, query).collect {
                updateState(apiResultToState(it, currentState))
            }
        }
    }


    private fun apiResultToState(
        apiResponseWrapper: ApiResponseWrapper<List<Restaurant>>,
        currentState: MainSearchViewState
    ): MainSearchViewState {
        return when (apiResponseWrapper) {
            is ApiResponseWrapper.Error -> currentState
            ApiResponseWrapper.Loading -> MainSearchViewState.Loading
            is ApiResponseWrapper.Success -> if (currentState is MainSearchViewState.ListState) MainSearchViewState.ListState(
                apiResponseWrapper.data
            )
            else MainSearchViewState.MapState(apiResponseWrapper.data)
        }

    }

    private fun hideShowListOverlay(currentState: MainSearchViewState) {
        if (currentState is MainSearchViewState.MapState) updateState(
            MainSearchViewState.ListState(
                currentState.restaurants
            )
        )
        else if (currentState is MainSearchViewState.ListState) updateState(
            MainSearchViewState.MapState(
                currentState.restaurants
            )
        )
    }

    override fun getState(): MainSearchViewState {
        return _state.value ?: MainSearchViewState.MapState(listOf(), null)
    }


}