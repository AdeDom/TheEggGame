package com.adedom.android.presentation.single

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.*
import com.adedom.teg.presentation.single.SingleViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_single.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SingleFragment : BaseFragment(R.layout.fragment_single) {

    private val viewModel by viewModel<SingleViewModel>()
    private var mMarkerMyLocation: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { googleMap ->
            googleMap.isMyLocationEnabled = true
            googleMap.setMinZoomPreference(12F)
            googleMap.setMaxZoomPreference(16F)

            lifecycleScope.launch {
                val location = locationProviderClient.awaitLastLocation()
                val latLng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14F)
                googleMap?.animateCamera(cameraUpdate)
            }
        }

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)
        }

        viewModel.error.observeError()

        btItem.setOnClickListener {
            viewModel.callItemCollection()
        }

        locationProviderClient
            .locationFlow()
            .conflate()
            .catch { e ->
                context.toast(e.message, Toast.LENGTH_LONG)
            }
            .asLiveData()
            .observe { location ->
                lifecycleScope.launch {
                    onLocationResult(location)
                }
            }

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_singleFragment_to_backpackFragment)
        }
    }

    private suspend fun onLocationResult(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)

        val playerInfo = viewModel.getDbPlayerInfo()

        val markerOptions = MarkerOptions().apply {
            position(latLng)
            title(playerInfo?.name)
            snippet(getString(R.string.level, playerInfo?.level))
        }

        context?.setImageCircle(playerInfo?.image, onResourceReady = { bitmap ->
            lifecycleScope.launch {
                mMarkerMyLocation?.remove()

                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                mMarkerMyLocation = mapView.getGoogleMap().addMarker(markerOptions)
            }
        }, onLoadCleared = { bitmap ->
            lifecycleScope.launch {
                mMarkerMyLocation?.remove()

                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                mMarkerMyLocation = mapView.getGoogleMap().addMarker(markerOptions)
            }
        })

    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}
