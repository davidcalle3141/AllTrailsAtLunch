package com.example.atlunch.ui.viewmodel

import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.common.MVIBaseViewModel
import com.example.atlunch.data.models.NetworkResponse
import com.example.atlunch.data.models.UserLocation
import com.example.atlunch.data.repositories.RestaurantRepo
import com.example.atlunch.ui.mviModels.MainSearchActions
import com.example.atlunch.ui.mviModels.MainSearchIntents
import com.example.atlunch.ui.mviModels.MainSearchViewState
import kotlinx.coroutines.flow.collect
import retrofit2.Response

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
                intent.userLocation
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
                action.userLocation,
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
        userLocation: UserLocation,
        currentState: MainSearchViewState
    ) {
        launchTask {
            repo.getRestaurants(userLocation, query).collect {
                updateState(apiResultToState(it, currentState))
            }
        }
    }


    //TODO add an error state or an singleLive event that indicates an error
    private fun apiResultToState(
        apiResponseWrapper: ApiResponseWrapper<Response<NetworkResponse>>,
        currentState: MainSearchViewState
    ): MainSearchViewState {
        return when (apiResponseWrapper) {
            is ApiResponseWrapper.Error -> currentState
            ApiResponseWrapper.Loading -> MainSearchViewState.Loading
            is ApiResponseWrapper.Success -> if (currentState is MainSearchViewState.ListState) MainSearchViewState.ListState(
                apiResponseWrapper.data.body()?.results ?: listOf()
            )
            else MainSearchViewState.MapState(apiResponseWrapper.data.body()?.results ?: listOf())
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