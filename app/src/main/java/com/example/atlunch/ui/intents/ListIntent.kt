package com.example.atlunch.ui.intents

sealed class ListIntent{
    object showMapIntent : ListIntent()
    data class selectRestaurantIntent(val position : Int): ListIntent()
}
