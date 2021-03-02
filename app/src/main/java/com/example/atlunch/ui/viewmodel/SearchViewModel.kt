package com.example.atlunch.ui.viewmodel

import android.util.Log
import com.example.atlunch.common.ApiResponseWrapper
import com.example.atlunch.common.Event
import com.example.atlunch.common.MVIBaseViewModel
import com.example.atlunch.data.models.Restaurant
import com.example.atlunch.data.repositories.RestaurantRepo
import com.example.atlunch.ui.mviModels.MainActions
import com.example.atlunch.ui.mviModels.MainActions.*
import com.example.atlunch.ui.mviModels.MainIntents
import com.example.atlunch.ui.mviModels.MainIntents.*
import com.example.atlunch.ui.mviModels.MainViewState
import com.example.atlunch.ui.mviModels.MainViewState.*


import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collect

class SearchViewModel(private val repo: RestaurantRepo, private val locationEnabled: Boolean) :
    MVIBaseViewModel<Restaurant, MainIntents, MainActions, MainViewState>() {
    val TAG = SearchViewModel::class.java.simpleName

    var lastUsedLocation: LatLng? = null
    fun isLocationEnabled() = locationEnabled

    init {
        launchTask { repo.initFavorites() }

    }

    override fun intentToAction(intent: MainIntents): MainActions {
        return when (intent) {
            is GetPlacesIntent -> SearchRestaurantsAction(
                intent.search,
                intent.userLocation
            )
            is FavoriteClickedIntent -> HandleFavoriteAction(intent.place_id, intent.position)
            is ScrollHorizontalListIntent -> HighlightPinAction(intent.position, false)
            is SelectRestaurantIntent -> HighlightPinAction(intent.position)
            ShowListIntent -> ShowListAction
            ShowMapIntent -> HideListAction
        }
    }

    override fun handleAction(action: MainActions) {
        val currentState = getState().peekContent()
        when (action) {
            is HighlightPinAction -> onHighlightMapPin(
                action.position,
                currentState,
                action.scroll
            )
            is SearchRestaurantsAction -> onSearchRestaurants(
                action.string,
                action.userLocation,
                currentState
            )
            is HandleFavoriteAction -> handleFavorite(
                currentState,
                action.place_id,
                action.position
            )
            HideListAction -> hideList(currentState)
            ShowListAction -> showList(currentState)
        }
    }

    private fun handleFavorite(currentState: MainViewState, placeId: String, position: Int) {
        launchTask {
            val currentList = currentState.list
            if (repo.toggleFavorite(placeId) && position < currentList.size) {
                val newList = arrayListOf<Restaurant>()
                currentList.forEach {
                    if (it.place_id == placeId) {
                        newList.add(it.copy(isFavorite = repo.isFavorite(placeId)))
                    } else newList.add(it)
                }
                when (currentState) {
                    is ShowListState -> updateState(currentState.copy(list = newList))
                    is LoadingState -> updateState(currentState.copy(list = newList))
                    is MapState -> updateState(currentState.copy(list = newList))
                    is SearchResultState -> updateState(MapState(list = newList))
                }
            }

        }

    }

    private fun onHighlightMapPin(
        position: Int,
        currentState: MainViewState,
        scrollToPosition: Boolean = true
    ) {
        when (currentState) {
            is MapState -> {
                updateState(
                    currentState.copy(
                        selected = position,
                        scrollToSelected = scrollToPosition
                    )
                )
            }
            is ShowListState -> {
                updateState(MapState(currentState.list))
            }
            is SearchResultState -> updateState(
                MapState(
                    currentState.list,
                    position,
                    scrollToPosition
                )
            )
        }
    }

    private fun onSearchRestaurants(
        query: String?,
        userLocation: LatLng?,
        currentState: MainViewState
    ) {
        val location = userLocation ?: lastUsedLocation
        location?.let {
            lastUsedLocation = location
            launchTask {
                repo.getRestaurants(it, query).collect {
                    updateState(apiResultToState(it, currentState))
                }
            }
        }

    }


    //TODO add an error state or an event that indicates a Toast
    private fun apiResultToState(
        apiResponseWrapper: ApiResponseWrapper<List<Restaurant>>,
        currentState: MainViewState
    ): MainViewState {
        return when (apiResponseWrapper) {
            is ApiResponseWrapper.Error -> currentState
            is ApiResponseWrapper.Loading -> LoadingState(listOf())
            is ApiResponseWrapper.Success -> SearchResultState(
                apiResponseWrapper.data
            )
        }

    }


    private fun showList(currentState: MainViewState) {
        if (currentState is LoadingState) updateState(MapState(listOf()))
        else updateState(ShowListState(currentState.list))
        Log.d(TAG, " show list ")
    }

    private fun hideList(currentState: MainViewState) {
        if (currentState is LoadingState) updateState(MapState(listOf()))
        else updateState(MapState(currentState.list))
        Log.d(TAG, "hide list")
    }

    override fun getState(): Event<MainViewState> {
        return _state.value ?: Event(MapState(listOf()))
    }



}