package com.example.atlunch.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class MVIBaseViewModel<T, INTENT : IViewIntent, ACTION : IViewAction, STATE : IListViewState<T>> : ViewModel(),
    IMVIModel<STATE, INTENT> {
    private lateinit var oldState : Event<STATE>
        protected val _state = MutableLiveData<Event<STATE>>()
    override val state: LiveData<Event<STATE>> = _state

    fun launchTask(block: suspend CoroutineScope.()-> Unit): Job {
        return viewModelScope.launch {
            block()
        }
    }

    fun updateState(newState: STATE){
        oldState = getState()
        _state.postValue(Event(newState))
    }

    override fun dispatchIntent(intent: INTENT) {
        handleAction(intentToAction(intent))
    }

    fun getOldState() = oldState.peekContent()
    abstract fun intentToAction(intent: INTENT): ACTION
    abstract fun handleAction(action: ACTION)
    abstract fun getState(): Event<STATE>


}