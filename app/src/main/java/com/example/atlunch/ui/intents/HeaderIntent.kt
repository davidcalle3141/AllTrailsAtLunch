package com.example.atlunch.ui.intents

sealed class HeaderIntent {
    data class GetSearchResults(val search : String) : HeaderIntent()
    object ClearSearch : HeaderIntent()
}