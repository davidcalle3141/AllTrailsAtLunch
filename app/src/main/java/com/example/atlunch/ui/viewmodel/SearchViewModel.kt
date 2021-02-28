package com.example.atlunch.ui.viewmodel

import com.example.atlunch.data.repositories.RestaurantRepo
import com.example.atlunch.ui.viewState.MainSearchViewState
import com.example.atlunch.common.BaseMVIViewModel
import com.example.atlunch.ui.actions.MainSearchActions
import com.example.atlunch.ui.intents.MainSearchIntents

class SearchViewModel(val repo: RestaurantRepo ): BaseMVIViewModel<MainSearchIntents,MainSearchActions,MainSearchViewState>() {


    override fun intentToAction(intent: MainSearchIntents): MainSearchActions {
        TODO("Not yet implemented")
    }

    override fun handleAction(action: MainSearchActions) {
        TODO("Not yet implemented")
    }


}