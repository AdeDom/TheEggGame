package com.adedom.teg.presentation.roominfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.response.CurrentRoomNoResponse
import com.adedom.teg.presentation.usercase.MultiUseCase
import kotlinx.coroutines.launch

class RoomInfoViewModel(
    private val useCase: MultiUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomInfoViewState>(RoomInfoViewState()) {

    private val _currentRoomNo = MutableLiveData<CurrentRoomNoResponse>()
    val currentRoomNo: LiveData<CurrentRoomNoResponse>
        get() = _currentRoomNo

    private val _leaveRoomInfoEvent = MutableLiveData<BaseResponse>()
    val leaveRoomInfoEvent: LiveData<BaseResponse>
        get() = _leaveRoomInfoEvent

    fun incomingRoomInfoTitle() {
        launch {
            setState { copy(loading = true) }

            repository.incomingRoomInfoTitle { roomInfoTitleOutgoing ->
                setState { copy(loading = false) }

                if (state.value?.roomNo == roomInfoTitleOutgoing.roomNo) {
                    setState { copy(roomInfoTitle = roomInfoTitleOutgoing.roomInfoTitle) }
                }
            }
        }
    }

    fun incomingRoomInfoPlayers() {
        launch {
            setState { copy(loading = true) }

            repository.incomingRoomInfoPlayers { roomInfoPlayersOutgoing ->
                setState { copy(loading = false) }

                setState { copy(roomInfoPlayers = roomInfoPlayersOutgoing.roomInfoPlayers) }
            }
        }
    }

    fun callMultiItemCollection() {
        launch {
            setState { copy(loading = true) }

            val request = MultiItemCollectionRequest(
                itemId = 999,
                qty = 500,
                latitude = 13.5231001,
                longitude = 100.7517565,
            )

            when (val resource = useCase.callMultiItemCollection(request)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun callCurrentRoomNo() {
        launch {
            setState { copy(loading = true) }

            when (val resource = repository.callCurrentRoomNo()) {
                is Resource.Success -> _currentRoomNo.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun callLeaveRoomInfo() {
        launch {
            setState { copy(loading = true) }

            when (val resource = useCase.callLeaveRoomInfo()) {
                is Resource.Success -> _leaveRoomInfoEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun setStateRoomNo(roomNo: String?) {
        setState { copy(roomNo = roomNo) }
    }

}
