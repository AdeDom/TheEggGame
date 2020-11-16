package com.adedom.android.presentation.single

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.*
import com.adedom.teg.presentation.single.SingleViewModel
import com.adedom.teg.presentation.single.SingleViewState
import com.adedom.teg.util.TegLatLng
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_single.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SingleFragment : BaseFragment(R.layout.fragment_single) {

    private val viewModel by viewModel<SingleViewModel>()
    private var mMarkerMyLocation: Marker? = null
    private val mMarkerSingleItems by lazy { mutableListOf<Marker>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        viewModel.attachFirstTime.observe {
            viewModel.callChangeCurrentModeSingle()
            viewModel.incomingSinglePeopleAll()
            viewModel.incomingSingleItem()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                .onEach { viewModel.setStateLatLng(TegLatLng(it.latitude, it.longitude)) }
                .catch { rootLayout.snackbar(it.message) }
                .launchIn(this)
        }

        viewModel.state.observe { state ->
            progressBar.setVisibility(state.loading)

            tvPeopleAll.text = state.peopleAll.toString()

            // marker
            setMarkerMyLocation(state)

            // marker item
            setMarkerItem(state)
        }

        viewModel.error.observeError()

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            context?.setImageCircle(playerInfo.image, onResourceReady = { bitmap ->
                viewModel.setStateBitmap(bitmap)
            }, onLoadCleared = { bitmap ->
                viewModel.setStateBitmap(bitmap)
            })
        })

        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_singleFragment_to_backpackFragment)
        }
    }

    private fun setMarkerMyLocation(state: SingleViewState) {
        launch {
            val googleMap = mapView.getGoogleMap()

            val latLng = LatLng(state.latLng.latitude, state.latLng.longitude)

            val markerOptions = MarkerOptions().apply {
                position(latLng)
                title(state.name)
                snippet(getString(R.string.level, state.level))
            }

            state.bitmap?.let {
                mMarkerMyLocation?.remove()

                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(state.bitmap))
                mMarkerMyLocation = googleMap.addMarker(markerOptions)
            }
        }
    }

    private fun setMarkerItem(state: SingleViewState) {
        launch {
            val googleMap = mapView.getGoogleMap()

            for (marker in mMarkerSingleItems) {
                marker.remove()
            }
            mMarkerSingleItems.clear()

            state.singleItems
                .filter { it.latitude != null && it.longitude != null }
                .forEach {
                    val markerOptions = MarkerOptions().apply {
                        position(LatLng(it.latitude!!, it.longitude!!))
                        icon(BitmapDescriptorFactory.defaultMarker())
                        title("Title")
                        snippet("Snippet")
                    }

                    mMarkerSingleItems.add(googleMap.addMarker(markerOptions))
                }
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
