package com.example.atlunch.ui.intents

sealed class MapIntent{
    object ShowListOverLay : MapIntent()
    object RecenterMapView : MapIntent()
    object SelectPin: MapIntent()
    data class ScrollHorizontalList(val position : Int): MapIntent()
}
