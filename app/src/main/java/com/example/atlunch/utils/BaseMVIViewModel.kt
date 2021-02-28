package com.example.atlunch.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseMVIViewModel<T> : ViewModel() {
    protected val _viewState : MutableLiveData<T> = MutableLiveData()
    val viewStateLiveData: LiveData<T> = _viewState

    protected fun makeRequest(block: suspend  () -> Unit): Job {
        return viewModelScope.launch {
           block()
        }
    }
    protected abstract fun renderState(state : T)
    fun getViewState() = _viewState.value

}