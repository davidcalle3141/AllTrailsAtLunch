package com.example.atlunch.common

import androidx.lifecycle.LiveData

interface IViewState

interface IViewIntent

interface IViewAction

interface IMVIModel<STATE,INTENT>{
    val state: LiveData<STATE>
    fun dispatchIntent(intent: INTENT)
}