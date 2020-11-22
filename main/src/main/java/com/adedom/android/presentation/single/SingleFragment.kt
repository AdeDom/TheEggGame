package com.adedom.android.presentation.single

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.*
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.presentation.single.SingleViewModel
import com.adedom.teg.presentation.single.SingleViewState
import com.adedom.teg.util.TegConstant
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
    private val mMarkerSinglePlayers by lazy { mutableListOf<Marker>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        viewModel.attachFirstTime.observe {
            viewModel.callFetchItemCollection()
            viewModel.callChangeCurrentModeSingle()
            viewModel.incomingSinglePeopleAll()
            viewModel.incomingSingleSuccessAnnouncement()
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

            val tegLatLng = TegLatLng(location.latitude, location.longitude)
            viewModel.incomingSingleItemAround(tegLatLng)
            viewModel.incomingPlaygroundSinglePlayer(tegLatLng)

            locationProviderClient
                .locationFlow()
                .onEach {
                    viewModel.setStateLatLng(TegLatLng(it.latitude, it.longitude))
                    viewModel.outgoingPlaygroundSinglePlayer(TegLatLng(it.latitude, it.longitude))
                }
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

            if (state.isValidateDistanceBetween) {
                viewModel.callSingleItemCollection()
            }

            if (state.isSingleSuccessAnnouncement) {
                singleSuccessAnnouncement(state)
            }

            if (state.players.isNotEmpty()) {
                setMarkerPlayers(state)
            }
        }

        viewModel.error.observeError()

        viewModel.getDbPlayerInfoLiveData.observe(viewLifecycleOwner, { playerInfo ->
            if (playerInfo == null) return@observe

            viewModel.setStateName(playerInfo.name)
            viewModel.setStateLevel(playerInfo.level)

            launch {
                val bitmap = context?.setImageCircle(playerInfo.image, playerInfo.gender)
                viewModel.setStateBitmap(bitmap)
            }
        })

        viewModel.getDbBackpackLiveData.observe(viewLifecycleOwner, { backpackItem ->
            if (backpackItem == null) return@observe

            tvEggI.text = getString(R.string.egg_i, backpackItem.eggI)
            tvEggII.text = getString(R.string.egg_ii, backpackItem.eggII)
            tvEggIII.text = getString(R.string.egg_iii, backpackItem.eggIII)
        })

        viewModel.isBackpackItemEvent.observe { isBackpackItem ->
            viewShadow.setVisibility(isBackpackItem)

            if (!isBackpackItem) {
                floatingActionButton.setImageResource(R.drawable.ic_backpack_white)
                tvEggI.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_close))
                tvEggII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_close))
                tvEggIII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_close))
                fabEggI.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_close))
                fabEggII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_close))
                fabEggIII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_close))
            } else {
                floatingActionButton.setImageResource(R.drawable.ic_close_white)
                tvEggI.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_open))
                tvEggII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_open))
                tvEggIII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_open))
                fabEggI.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_open))
                fabEggII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_open))
                fabEggIII.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fab_open))
            }
        }

        floatingActionButton.setOnClickListener {
            viewModel.setStateBackpackItem()
        }

        viewShadow.setOnClickListener { viewModel.setStateBackpackItem() }
    }

    private fun singleSuccessAnnouncement(state: SingleViewState) {
        val image: Int = when (state.singleSuccessAnnouncement?.itemId) {
            TegConstant.SINGLE_ITEM_ONE -> R.drawable.ic_egg_i
            TegConstant.SINGLE_ITEM_TWO -> R.drawable.ic_egg_ii
            TegConstant.SINGLE_ITEM_THREE -> R.drawable.ic_egg_iii
            TegConstant.ITEM_LEVEL -> R.drawable.ic_egg
            else -> 0
        }

        context.toastTegSingle(
            image,
            state.singleSuccessAnnouncement?.qty,
            state.singleSuccessAnnouncement?.name
        )
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

            state.singleItems.forEach {
                val bmp = when (it.itemTypeId) {
                    1 -> context?.convertLayoutMarkerItem(R.drawable.ic_egg)
                    2 -> context?.convertLayoutMarkerItem(R.drawable.ic_mystery_box)
                    3 -> context?.convertLayoutMarkerItem(R.drawable.ic_mystery_item)
                    4 -> context?.convertLayoutMarkerItem(R.drawable.ic_egg_bonus)
                    else -> null
                }

                val title = when (it.itemTypeId) {
                    1 -> getString(R.string.exp)
                    2 -> getString(R.string.mystery_box)
                    3 -> getString(R.string.mystery_item)
                    4 -> getString(R.string.bonus)
                    else -> null
                }

                val markerOptions = MarkerOptions().apply {
                    position(LatLng(it.latitude!!, it.longitude!!))
                    icon(BitmapDescriptorFactory.fromBitmap(bmp))
                    title(title)
                }

                mMarkerSingleItems.add(googleMap.addMarker(markerOptions))
            }
        }
    }

    private fun setMarkerPlayers(state: SingleViewState) {
        launch {
            val googleMap = mapView.getGoogleMap()

            for (marker in mMarkerSinglePlayers) {
                marker.remove()
            }
            mMarkerSinglePlayers.clear()

            state.players.forEach {
                val bitmap = context?.setImageCircle(it.image, it.gender)

                val markerOptions = MarkerOptions().apply {
                    position(LatLng(it.latitude!!, it.longitude!!))
                    icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    title(it.name)
                    snippet(getString(R.string.level, it.level))
                }
                mMarkerSinglePlayers.add(googleMap.addMarker(markerOptions))
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
