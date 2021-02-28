package com.example.atlunch.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class MVIFragment<INTENT : IViewIntent, STATE : IViewState, ACTION : IViewAction, VM : MVIBaseViewModel<INTENT, ACTION, STATE>> :
    Fragment() {
    private lateinit var viewState: STATE
    private lateinit var viewModel: VM
    fun getState() = viewState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewMode().state.observe(viewLifecycleOwner, {
            renderUIFromState(it)
        })
    }

    fun dispatchIntent(intent: INTENT) = viewModel.dispatchIntent(intent)
    abstract fun getViewMode(): VM
    abstract fun renderUIFromState(state: STATE)
}