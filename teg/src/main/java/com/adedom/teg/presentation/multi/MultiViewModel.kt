package com.adedom.teg.presentation.multi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.presentation.usercase.MultiUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MultiViewModel(
    private val useCase: MultiUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<MultiViewState>(MultiViewState()) {

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    private val _markerMyLocation = MutableLiveData<TegLatLng>()
    val markerMyLocation: LiveData<TegLatLng>
        get() = _markerMyLocation

    var listener: TegMultiPlayerListener? = null

    fun callTimerTegMultiPlayer() {
        launch {
            when (val resource = useCase.callTimerTegMultiPlayer(listener)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    fun setMarkerMyLocation(tegLatLng: TegLatLng) {
        _markerMyLocation.value = tegLatLng
    }

}
