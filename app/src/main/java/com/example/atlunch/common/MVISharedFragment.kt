package com.example.atlunch.common

import androidx.fragment.app.Fragment

abstract class MVISharedFragment<INTENT : IViewIntent, STATE : IViewState, ACTION : IViewAction, VM : MVIBaseViewModel<INTENT, ACTION, STATE>> :
    Fragment() {
    private lateinit var viewState: STATE
    abstract val viewModel: VM


    fun getState() = viewState


    fun initObserveState() {
        viewModel.state.observe(viewLifecycleOwner, {
            renderUIFromState(it)
        })
    }

    fun dispatchIntent(intent: INTENT) = viewModel.dispatchIntent(intent)
    abstract fun renderUIFromState(state: STATE)
}