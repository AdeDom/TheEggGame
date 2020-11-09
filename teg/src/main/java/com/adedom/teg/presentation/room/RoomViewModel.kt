package com.adedom.teg.presentation.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.JoinRoomInfoRequest
import com.adedom.teg.models.response.BaseResponse
import kotlinx.coroutines.launch

class RoomViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomViewState>(RoomViewState()) {

    private val _joinRoomInfoEvent = MutableLiveData<BaseResponse>()
    val joinRoomInfo: LiveData<BaseResponse>
        get() = _joinRoomInfoEvent

    fun incomingRoomPeopleAll() {
        launch {
            repository.incomingRoomPeopleAll { roomPeopleAll ->
                setState { copy(peopleAll = roomPeopleAll.peopleAll) }
            }
        }
    }

    fun incomingPlaygroundRoom() {
        launch {
            setState { copy(loading = true) }

            repository.incomingPlaygroundRoom { rooms ->
                setState { copy(loading = false) }

                setState { copy(rooms = rooms.rooms) }
            }
        }
    }

    fun callJoinRoomInfo(roomNo: String?) {
        launch {
            setState { copy(loading = true) }

            val request = JoinRoomInfoRequest(roomNo)
            when (val resource = repository.callJoinRoomInfo(request)) {
                is Resource.Success -> _joinRoomInfoEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
