package com.example.atlunch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atlunch.R
import com.example.atlunch.common.MVISharedFragment
import com.example.atlunch.ui.mviModels.MainSearchActions
import com.example.atlunch.ui.mviModels.MainSearchIntents
import com.example.atlunch.ui.mviModels.MainSearchViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SearchHeaderFragment :
    MVISharedFragment<MainSearchIntents, MainSearchViewState, MainSearchActions, SearchViewModel>() {
    override val viewModel: SearchViewModel by sharedViewModel<SearchViewModel>()

    override fun renderUIFromState(state: MainSearchViewState) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_header, container, false)
    }




}