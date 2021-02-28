package com.example.atlunch.ui.fragments

import com.example.atlunch.common.MVIFragment
import com.example.atlunch.ui.mviModels.MainSearchActions
import com.example.atlunch.ui.mviModels.MainSearchIntents
import com.example.atlunch.ui.mviModels.MainSearchViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SearchHeaderFragment :
    MVIFragment<MainSearchIntents, MainSearchViewState, MainSearchActions, SearchViewModel>() {
    val sharedViewModel by  sharedViewModel<SearchViewModel>()
    override fun getViewMode(): SearchViewModel {
        TODO("Not yet implemented")
    }


    override fun renderUIFromState(state: MainSearchViewState) {
        TODO("Not yet implemented")
    }


}