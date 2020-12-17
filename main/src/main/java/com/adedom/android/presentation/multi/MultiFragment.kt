package com.adedom.android.presentation.multi

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.*
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.presentation.multi.MultiViewModel
import com.adedom.teg.presentation.multi.MultiViewState
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

    private val args by navArgs<MultiFragmentArgs>()
    private val viewModel by viewModel<MultiViewModel>()
    private var mMarkerMyLocation: Marker? = null
    private val mMarkerMultiItems by lazy { mutableListOf<Marker>() }

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
                .catch { requireView().snackbar(it.message) }
                .launchIn(this)
        }

        viewModel.listener = this

        viewModel.attachFirstTime.observe {
            viewModel.callTimerTegMultiPlayer()
            viewModel.incomingMultiPlayerItems()
            viewModel.incomingMultiPlayerScore()
            if (args.isRoleHead) {
                viewModel.callAddMultiItem()
            }
        }

        viewModel.state.observe { state ->
            renderUI(state)
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

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireActivity()).apply {
                        setTitle(R.string.dialog_multi_title)
                        setMessage(R.string.dialog_multi_message)
                        setPositiveButton(android.R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setNegativeButton(android.R.string.ok) { _, _ ->
                            findNavController().popBackStack()
                        }
                        show()
                    }
                }
            })
    }

    private fun renderUI(state: MultiViewState) {
        animationViewLoading.isVisible = state.isLoading

        // marker item
        setMarkerItem(state)

        if (state.isValidateDistanceBetween) {
            viewModel.callAddMultiScore()
        }

        // score
        tvScoreA.text = state.scoreTeamA.toString()
        tvScoreB.text = state.scoreTeamB.toString()
    }

    private fun setMarkerItem(state: MultiViewState) {
        launch {
            val googleMap = mapView.getGoogleMap()

            for (marker in mMarkerMultiItems) {
                marker.remove()
            }
            mMarkerMultiItems.clear()

            state.multiItems.forEach {
                val bmp = context?.convertLayoutMarkerItem(R.drawable.the_egg_game)

                val markerOptions = MarkerOptions().apply {
                    position(LatLng(it.latitude!!, it.longitude!!))
                    icon(BitmapDescriptorFactory.fromBitmap(bmp))
                }

                mMarkerMultiItems.add(googleMap.addMarker(markerOptions))
            }
        }
    }

    private fun onLocationChange(location: Location) {
        viewModel.setStateLatLng(TegLatLng(location.latitude, location.longitude))

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

    override fun onStart() {
        super.onStart()

        viewModel.callFetchMultiItem()
        viewModel.callFetchMultiScore()
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
