package com.adedom.teg.presentation.multi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.request.AddMultiScoreRequest
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

    fun callFetchMultiItem() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase.callFetchMultiItem()) {
                is Resource.Success -> setState { copy(multiItems = resource.data.multiItems) }
                is Resource.Error -> setError(resource)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callAddMultiItem() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase.callAddMultiItem()) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun incomingMultiPlayerItems() {
        launch {
            repository.incomingMultiPlayerItems { multiItemResponse ->
                setState {
                    copy(multiItems = multiItemResponse.multiItems)
                }
            }
            incomingMultiPlayerItems()
        }
    }

    fun setStateLatLng(latLng: TegLatLng) {
        setState {
            copy(
                latLng = latLng,
                isValidateDistanceBetween = useCase.isValidateDistanceBetween(
                    latLng,
                    state.value?.multiItems
                )
            )
        }
    }

    fun callAddMultiScore() {
        launch {
            setState { copy(isLoading = true, isValidateDistanceBetween = false) }

            val singleId = useCase.getMultiItemId(state.value?.latLng, state.value?.multiItems)

            when (val resource = useCase.callAddMultiScore(AddMultiScoreRequest(singleId))) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callFetchMultiScore() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase.callFetchMultiScore()) {
                is Resource.Success -> {
                    setState {
                        copy(
                            scoreTeamA = resource.data.score?.teamA,
                            scoreTeamB = resource.data.score?.teamB,
                        )
                    }
                }
                is Resource.Error -> setError(resource)
            }

            setState { copy(isLoading = false) }
        }
    }

}
