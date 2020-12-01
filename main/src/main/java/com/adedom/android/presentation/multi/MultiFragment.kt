package com.adedom.android.presentation.multi

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.*
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.presentation.multi.MultiViewModel
import com.adedom.teg.presentation.multi.TegMultiPlayerListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_multi.*
import kotlinx.android.synthetic.main.fragment_multi.animationViewLoading
import kotlinx.android.synthetic.main.fragment_multi.mapView
import kotlinx.android.synthetic.main.fragment_single.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MultiFragment : BaseFragment(R.layout.fragment_multi), TegMultiPlayerListener {

    private val viewModel by viewModel<MultiViewModel>()
    private var mMarkerMyLocation: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    @SuppressLint("MissingPermission")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val locationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireActivity())

        mapView.onCreate(savedInstanceState)

        launch {
            val googleMap = mapView.getGoogleMap()

            googleMap.isMyLocationEnabled = true
            googleMap.setMinZoomPreference(12F)
            googleMap.setMaxZoomPreference(16F)

            val location = locationProviderClient.awaitLastLocation()
            val latLng = LatLng(location.latitude, location.longitude)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14F)
            googleMap.animateCamera(cameraUpdate)

            locationProviderClient
                .locationFlow()
                .onEach { onLocationChange(it) }
                .catch { rootLayout.snackbar(it.message) }
                .launchIn(this)
        }

        viewModel.listener = this

        viewModel.attachFirstTime.observe {
            viewModel.callTimerTegMultiPlayer()
        }

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)
        }

        viewModel.markerMyLocation.observe {
            mMarkerMyLocation?.position = LatLng(it.latitude, it.longitude)
        }

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            launch {
                val location = locationProviderClient.awaitLastLocation()
                val latLng = LatLng(location.latitude, location.longitude)

                val markerOptions = MarkerOptions().apply {
                    position(latLng)
                    title(playerInfo.name)
                    snippet(getString(R.string.level, playerInfo.level))
                }

                val bitmap = context?.setImageCircle(playerInfo.image, playerInfo.gender)
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                mMarkerMyLocation?.remove()
                mMarkerMyLocation = mapView.getGoogleMap().addMarker(markerOptions)
            }
        })

        viewModel.error.observeError()
    }

    private fun onLocationChange(location: Location) {
        ValueAnimator.ofInt(0, 1).apply {
            duration = 3_000
            interpolator = LinearInterpolator()
            addUpdateListener {
                val fraction = it.animatedFraction
                val oldLatLng = viewModel.markerMyLocation.value ?: TegLatLng()
                val lat = fraction * location.latitude + (1 - fraction) * oldLatLng.latitude
                val lng = fraction * location.longitude + (1 - fraction) * oldLatLng.longitude

                viewModel.setMarkerMyLocation(TegLatLng(lat, lng))
            }
            start()
        }
    }

    override fun onTimerTegMultiPlayer(timer: String) {
        tvTimer.text = timer
    }

    override fun onEndTegMultiPlayer() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Title")
            setMessage("Message")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
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
