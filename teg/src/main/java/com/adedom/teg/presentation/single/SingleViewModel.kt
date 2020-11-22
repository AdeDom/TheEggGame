package com.adedom.teg.presentation.single

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.presentation.usercase.SingleUseCase
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SingleViewModel(
    private val useCase: SingleUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<SingleViewState>(SingleViewState()) {

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    val getDbBackpackLiveData: LiveData<BackpackEntity>
        get() = repository.getDbBackpackLiveData()

    private val _isBackpackItemEvent = MutableLiveData<Boolean>()
    val isBackpackItemEvent: LiveData<Boolean>
        get() = _isBackpackItemEvent

    fun incomingSinglePeopleAll() {
        launch {
            repository.incomingSinglePeopleAll { roomPeopleAllOutgoing ->
                setState { copy(peopleAll = roomPeopleAllOutgoing.peopleAll) }
            }
            incomingSinglePeopleAll()
        }
    }

    fun incomingSingleItemAround(latLng: TegLatLng) {
        launch {
            useCase.incomingSingleItem(latLng) { singleItemAroundOutgoing ->
                setState {
                    copy(singleItems = singleItemAroundOutgoing.singleItems)
                }
            }
            incomingSingleItemAround(latLng)
        }
    }

    fun incomingSingleSuccessAnnouncement() {
        launch {
            repository.incomingSingleSuccessAnnouncement {
                setState {
                    copy(
                        singleSuccessAnnouncement = it,
                        isSingleSuccessAnnouncement = true
                    )
                }
                setState { copy(isSingleSuccessAnnouncement = false) }
            }
            incomingSingleSuccessAnnouncement()
        }
    }

    fun incomingPlaygroundSinglePlayer(latLng: TegLatLng) {
        launch {
            useCase.incomingPlaygroundSinglePlayer(latLng) {
                setState { copy(players = it.players) }
            }
            incomingPlaygroundSinglePlayer(latLng)
        }
    }

    fun setStateLatLng(latLng: TegLatLng) {
        setState {
            copy(
                latLng = latLng,
                isValidateDistanceBetween = useCase.isValidateDistanceBetween(
                    latLng,
                    state.value?.singleItems
                )
            )
        }
    }

    fun outgoingPlaygroundSinglePlayer(latLng: TegLatLng) {
        launch {
            repository.outgoingPlaygroundSinglePlayer(latLng)
        }
    }

    fun setStateBitmap(bitmap: Bitmap?) {
        setState { copy(bitmap = bitmap) }
    }

    fun callSingleItemCollection() {
        launch {
            setState { copy(loading = true, isValidateDistanceBetween = false) }

            val singleId = useCase.getSingleItemId(state.value?.latLng, state.value?.singleItems)

            when (val resource = useCase.callSingleItemCollection(singleId)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun callChangeCurrentModeSingle() {
        launch {
            when (val resource = repository.callChangeCurrentMode(TegConstant.PLAY_MODE_SINGLE)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    fun setStateName(name: String?) {
        setState { copy(name = name) }
    }

    fun setStateLevel(level: Int?) {
        setState { copy(level = level) }
    }

    fun setStateBackpackItem() {
        _isBackpackItemEvent.value = !(isBackpackItemEvent.value ?: false)
    }

}
