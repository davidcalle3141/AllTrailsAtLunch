package com.example.atlunch.common

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseMVIViewModel<INTENT : IViewIntent, ACTION : IViewAction, STATE : IViewState>() : ViewModel(),
    IMVIModel<STATE, INTENT> {

        protected val _state = MutableLiveData<STATE>()
    override val state: LiveData<STATE> = _state

    fun launchTask(block: suspend CoroutineScope.()-> Unit): Job {
        return viewModelScope.launch {
            block()
        }
    }

    fun updateState(newState: STATE){
        _state.postValue(newState)
    }

    override fun dispatchIntent(intent: INTENT) {
        handleAction(intentToAction(intent))
    }

    abstract fun intentToAction(intent: INTENT): ACTION
    abstract fun handleAction(action: ACTION)
    abstract fun getState(): STATE


}