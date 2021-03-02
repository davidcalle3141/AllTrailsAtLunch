package com.example.atlunch.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.atlunch.R
import com.example.atlunch.common.MVISharedFragment
import com.example.atlunch.data.models.Restaurant
import com.example.atlunch.databinding.FragmentSearchMapBinding
import com.example.atlunch.ui.adapters.MarkerAdapter
import com.example.atlunch.ui.adapters.RestaurantAdapter
import com.example.atlunch.ui.mviModels.MainActions
import com.example.atlunch.ui.mviModels.MainIntents
import com.example.atlunch.ui.mviModels.MainViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SearchMapFragment :
    MVISharedFragment<Restaurant, MainIntents, MainViewState, MainActions, SearchViewModel>() {
    private var _binding: FragmentSearchMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapter: RestaurantAdapter
    private lateinit var markerAdapter: MarkerAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    //TODO find default location with IP or other means
    private val defaultLocation = LatLng(-122.446747, 37.733795)
    private lateinit var map: GoogleMap
    private var isMapReady = false

    override val viewModel by sharedViewModel<SearchViewModel>()

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isMapToolbarEnabled = false
        markerAdapter = MarkerAdapter(map)
        isMapReady  = true
        getLocation()
        initObserveState()
        setListeners()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        context?.let {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(it)
        }
        rvAdapter = RestaurantAdapter(::onFavoriteClickedListener)
        binding.searchMapFragmentRv.adapter = rvAdapter
    }


    @SuppressLint("MissingPermission")
    fun getLocation() {
        try {
            if (viewModel.isLocationEnabled() && viewModel.lastUsedLocation == null) {
                val locationResult =
                    fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModel.dispatchIntent(
                            MainIntents.GetPlacesIntent(
                                null,
                                LatLng(it.result.latitude, it.result.longitude)
                            )
                        )
                        if (isMapReady) {
                            map.moveCamera(
                                CameraUpdateFactory.newLatLng(
                                    LatLng(
                                        it.result.latitude,
                                        it.result.longitude
                                    )
                                )
                            )
                        }


                    } else {
                        viewModel.dispatchIntent(
                            MainIntents.GetPlacesIntent(
                                null,
                                defaultLocation
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Error getting location", e.message ?: "")
        }
    }


    override fun renderUIFromState(state: MainViewState) {
        if(state.list.isNotEmpty() && markerAdapter.getSize() == 0){
            markerAdapter.setData(state.list)
        }

        when (state) {
            is MainViewState.LoadingState -> setLoadingState()
            is MainViewState.MapState -> setMapState(state)
            is MainViewState.SearchResultState -> setSearchResultState(state)
        }
    }

    private fun setSearchResultState(state: MainViewState.SearchResultState) {
        val list = state.list
        rvAdapter.setData(list)
        markerAdapter.setData(list)
        if (isMapReady) map.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                markerAdapter.getBoundsBuilder().build(), 100
            )
        )

        markerAdapter.setMarkerSelected(0)
    }

    private fun setMapState(state: MainViewState.MapState) {
        val selected = state.selected
        when {
            selected != null && state.scrollToSelected == true -> {
                binding.searchMapFragmentRv.smoothScrollToPosition(selected)
                markerAdapter.setMarkerSelected(selected)

            }
            selected != null -> {
                markerAdapter.setMarkerSelected(selected)
                markerAdapter.getMarker(selected)?.position?.let {
                    if (isMapReady) map.animateCamera(CameraUpdateFactory.newLatLng(it))

                }
            }
        }
        rvAdapter.setData(state.list)
    }


    private fun setLoadingState() {
        rvAdapter.setData(listOf())
        markerAdapter.clear()
    }

    private fun onFavoriteClickedListener(place_id: String, position: Int) {
        dispatchIntent(MainIntents.FavoriteClickedIntent(place_id, position))
    }

    private fun setListeners() {
        binding.searchFragmentMapViewListBtn.setOnClickListener {
            dispatchIntent(MainIntents.ShowListIntent)
        }
        map.setOnMarkerClickListener {
            if (it.tag is Int) {
                dispatchIntent(MainIntents.SelectRestaurantIntent(it.tag as Int))
            }
            return@setOnMarkerClickListener false
        }
        binding.searchMapFragmentRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        dispatchIntent(
                            MainIntents.ScrollHorizontalListIntent(
                                layoutManager.findFirstCompletelyVisibleItemPosition()
                            )
                        )
                    }
                }
            }
        })


    }

}