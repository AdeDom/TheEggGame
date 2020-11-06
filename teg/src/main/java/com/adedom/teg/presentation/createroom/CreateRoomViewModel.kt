package com.adedom.teg.presentation.createroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.websocket.CreateRoomIncoming
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

    fun setStateRoomName(roomName: String) {
        setState { copy(roomName = roomName) }
    }

    fun outgoingCreateRoom() {
        launch {
            setState { copy(loading = true) }

            val createRoom = CreateRoomIncoming(
                roomName = state.value?.roomName,
                roomPeople = state.value?.roomPeople,
            )
            repository.outgoingCreateRoom(createRoom)

            setState { copy(loading = false) }
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
