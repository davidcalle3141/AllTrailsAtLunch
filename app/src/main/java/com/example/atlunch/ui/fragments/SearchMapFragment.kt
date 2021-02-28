package com.example.atlunch.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atlunch.R
import com.example.atlunch.common.MVISharedFragment
import com.example.atlunch.data.models.UserLocation
import com.example.atlunch.ui.mviModels.MainSearchActions
import com.example.atlunch.ui.mviModels.MainSearchIntents
import com.example.atlunch.ui.mviModels.MainSearchViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchMapFragment : MVISharedFragment<MainSearchIntents,MainSearchViewState,MainSearchActions,SearchViewModel>() {
    override val viewModel by  sharedViewModel<SearchViewModel>()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val defaultLocation = UserLocation(-122.446747, 37.733795)


    private val callback = OnMapReadyCallback { googleMap ->
        getLocation()
        initObserveState()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        context?.let {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
        }
    }


    @SuppressLint("MissingPermission")
    fun getLocation(){
        try {
            if(viewModel.isLocationEnabled()){
                val locationResult =
                    fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener {
                    if(it.isSuccessful) {
                        viewModel.dispatchIntent(MainSearchIntents.GetSearchResults(null,
                            UserLocation(it.result.longitude,it.result.latitude)
                        ))
                    } else{
                        viewModel.dispatchIntent(MainSearchIntents.GetSearchResults(null,
                           defaultLocation
                        ))
                    }
                }
            }
        }catch (e : Exception){
            //TODO
        }
    }


    override fun renderUIFromState(state: MainSearchViewState) {
            when(state){
                is MainSearchViewState.ListState -> TODO()
                MainSearchViewState.Loading -> TODO()
                is MainSearchViewState.MapState -> TODO()
            }
    }

    fun setMapState(){

    }

    fun setListState(){

    }

    fun setLoadingState(){

    }



}