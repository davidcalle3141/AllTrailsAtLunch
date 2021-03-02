package com.example.atlunch.common

import androidx.lifecycle.LiveData

interface IListViewState<T>{
    val list : List<T>
}

interface IViewIntent

interface IViewAction

interface IMVIModel<STATE,INTENT>{
    val state: LiveData<Event<STATE>>
    fun dispatchIntent(intent: INTENT)
}