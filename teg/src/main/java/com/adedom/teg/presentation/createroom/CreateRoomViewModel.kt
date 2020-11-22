package com.adedom.teg.presentation.createroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.CreateRoomRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.CreateRoomUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class CreateRoomViewModel(
    private val useCase: CreateRoomUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<CreateRoomViewState>(CreateRoomViewState()) {

    private val channel = ConflatedBroadcastChannel<CreateRoomViewEvent>()

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    private val _createRoomEvent = MutableLiveData<BaseResponse>()
    val createRoomEvent: LiveData<BaseResponse>
        get() = _createRoomEvent

    fun setStateRoomName(roomName: String) {
        setState { copy(roomName = roomName) }
    }

    fun callCreateRoom() {
        launch {
            setState { copy(loading = true, isClickable = false) }

            val request = CreateRoomRequest(
                roomName = state.value?.roomName,
                roomPeople = state.value?.roomPeople,
            )
            when (val resource = useCase.callCreateRoom(request)) {
                is Resource.Success -> _createRoomEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false, isClickable = true) }
        }
    }

    fun process(event: CreateRoomViewEvent) {
        launch {
            channel.send(event)
        }
    }

    init {
        channel.asFlow()
            .onEach { event ->
                when (event) {
                    CreateRoomViewEvent.ArrowLeft -> {
                        setState { copy(roomPeople = useCase.validateMinRoomPeople(roomPeople)) }
                    }
                    CreateRoomViewEvent.ArrowRight -> {
                        setState { copy(roomPeople = useCase.validateMaxRoomPeople(roomPeople)) }
                    }
                }
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(viewModelScope)
    }

}
