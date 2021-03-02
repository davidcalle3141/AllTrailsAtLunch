package com.example.atlunch.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.atlunch.R
import com.example.atlunch.ui.fragments.SearchHeaderFragment
import com.example.atlunch.ui.fragments.SearchListFragment
import com.example.atlunch.ui.fragments.SearchMapFragment
import com.example.atlunch.ui.mviModels.MainViewState
import com.example.atlunch.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {
    private var locationPermissionGranted = false
    private var viewModel: SearchViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            getRequestPermissionsAndInitUI()
        } else viewModel = getViewModel()

        initObserver()

    }

    private fun initObserver() {
        viewModel?.state?.observe(this, {
            it.getContentIfNotHandled {state ->
                when (state) {
                    is MainViewState.ShowListState -> {
                        if (viewModel?.getOldState() !is MainViewState.ShowListState)
                            showList()
                    }
                    else -> {
                        if (viewModel?.getOldState() is MainViewState.ShowListState)
                            hideList()
                    }
                }
            }
        })
    }

    private fun initUI() {
        viewModel = getViewModel { parametersOf(locationPermissionGranted) }
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<SearchHeaderFragment>(R.id.main_activity_header_fragment_holder)
            add<SearchMapFragment>(R.id.main_activity_ui_fragment_holder)
        }
    }

    private fun getRequestPermissionsAndInitUI() {

        locationPermissionGranted = false
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            initUI()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }

        initUI()
    }

    private fun showList() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(R.anim.slide_up, 0, 0, R.animator.slide_down)
            addToBackStack(null)
            add<SearchListFragment>(
                R.id.main_activity_ui_fragment_holder,
                SearchListFragment.FRAGMENT_TAG
            )
        }

    }

    private fun hideList() {
        supportFragmentManager.popBackStack()

    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    }

}