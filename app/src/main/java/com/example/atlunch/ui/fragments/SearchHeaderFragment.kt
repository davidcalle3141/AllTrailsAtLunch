package com.example.atlunch.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atlunch.common.MVISharedFragment
import com.example.atlunch.data.models.Restaurant
import com.example.atlunch.databinding.FragmentSearchHeaderBinding
import com.example.atlunch.ui.mviModels.MainActions
import com.example.atlunch.ui.mviModels.MainIntents
import com.example.atlunch.ui.mviModels.MainViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SearchHeaderFragment :
    MVISharedFragment<Restaurant, MainIntents, MainViewState, MainActions, SearchViewModel>() {
    private var _binding: FragmentSearchHeaderBinding? = null
    private val binding get() = _binding!!
    override val viewModel: SearchViewModel by sharedViewModel<SearchViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    //TODO hide keyboard when on enter clicked or when focus changed
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserveState()
        binding.searchFragmentSearchBar.roundedSearchViewEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                viewModel.dispatchIntent(MainIntents.GetPlacesIntent(
                        binding.searchFragmentSearchBar.roundedSearchViewEditText.text.toString(), null
                ))
            }
            false
        }

    }

    override fun renderUIFromState(state: MainViewState) {
        val magnifierView = binding.searchFragmentSearchBar.roundedSearchViewImage
        val lottieView = binding.searchFragmentSearchBar.roundedSearchViewLottie

        when(state){
            is MainViewState.LoadingState -> {
                magnifierView.visibility = View.INVISIBLE
                lottieView.visibility = View.VISIBLE
                lottieView.playAnimation()
            }
            else ->{
                magnifierView.visibility = View.VISIBLE
                lottieView.visibility = View.INVISIBLE
            }
        }
    }

}