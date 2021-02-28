package com.example.atlunch.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel

abstract class MVISharedFragment<INTENT : IViewIntent, STATE : IViewState, ACTION : IViewAction, VM : MVIBaseViewModel<INTENT, ACTION, STATE>> :
    Fragment() {
    private lateinit var viewState: STATE
    abstract val viewModel: VM


    fun getState() = viewState
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, {
            renderUIFromState(it)
        })
    }

    fun dispatchIntent(intent: INTENT) = viewModel.dispatchIntent(intent)
    abstract fun renderUIFromState(state: STATE)
}