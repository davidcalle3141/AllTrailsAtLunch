package com.example.atlunch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atlunch.common.MVISharedFragment
import com.example.atlunch.data.models.Restaurant
import com.example.atlunch.databinding.FragmentSearchListBinding
import com.example.atlunch.ui.adapters.RestaurantAdapter
import com.example.atlunch.ui.mviModels.MainActions
import com.example.atlunch.ui.mviModels.MainIntents
import com.example.atlunch.ui.mviModels.MainViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SearchListFragment : MVISharedFragment<Restaurant, MainIntents, MainViewState, MainActions, SearchViewModel>() {

    override val viewModel: SearchViewModel by sharedViewModel<SearchViewModel>()
    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RestaurantAdapter


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserveState()
        initListeners()

        adapter = RestaurantAdapter(::onFavoriteClickedListener)
        binding.searchListRv.adapter = adapter
    }

    private fun initListeners() {
        binding.searchListBtn.setOnClickListener {
            dispatchIntent(MainIntents.ShowMapIntent)
        }
    }

    override fun renderUIFromState(state: MainViewState) {
        if(state is MainViewState.ShowListState){
            adapter.setData(state.list)
        }
    }

    private fun onFavoriteClickedListener(place_id: String, position : Int){
        dispatchIntent(MainIntents.FavoriteClickedIntent(place_id, position))
    }


    companion object{
        const val FRAGMENT_TAG = "SearchListFragment"
    }
}