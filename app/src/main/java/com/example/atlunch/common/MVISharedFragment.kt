package com.example.atlunch.common

import androidx.fragment.app.Fragment

abstract class MVISharedFragment<T, INTENT : IViewIntent, STATE : IListViewState<T>, ACTION : IViewAction, VM : MVIBaseViewModel<T,INTENT, ACTION, STATE>> :
    Fragment() {
    private lateinit var viewState: STATE
    abstract val viewModel: VM


    fun getState() = viewState


    fun initObserveState() {
        viewModel.state.observe(viewLifecycleOwner, {
            renderUIFromState(it.peekContent())
        })
    }

    fun dispatchIntent(intent: INTENT) = viewModel.dispatchIntent(intent)
    abstract fun renderUIFromState(state: STATE)
}