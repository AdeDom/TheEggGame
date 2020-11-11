package com.adedom.teg.presentation.roominfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.models.response.CurrentRoomNoResponse
import com.adedom.teg.presentation.usercase.RoomInfoUseCase
import com.adedom.teg.util.TegConstant
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
class RoomInfoViewModel(
    private val useCase: RoomInfoUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomInfoViewState>(RoomInfoViewState()) {

    private val channel = ConflatedBroadcastChannel<RoomInfoViewEvent>()

    var listener: RoomInfoTegMultiListener? = null

    private val _currentRoomNo = MutableLiveData<CurrentRoomNoResponse>()
    val currentRoomNo: LiveData<CurrentRoomNoResponse>
        get() = _currentRoomNo

    private val _leaveRoomInfoEvent = MutableLiveData<BaseResponse>()
    val leaveRoomInfoEvent: LiveData<BaseResponse>
        get() = _leaveRoomInfoEvent

    private val _goTegMultiEvent = MutableLiveData<BaseResponse>()
    val goTegMultiEvent: LiveData<BaseResponse>
        get() = _goTegMultiEvent

    fun incomingRoomInfoTitle(roomNo: String?) {
        launch {
            setState { copy(loading = true) }

            useCase.incomingRoomInfoTitle(roomNo) { roomInfoTitleOutgoing ->
                setState {
                    copy(
                        loading = false,
                        roomInfoTitle = roomInfoTitleOutgoing.roomInfoTitle,
                    )
                }
            }
        }
    }

    fun incomingRoomInfoPlayers(roomNo: String?) {
        launch {
            setState { copy(loading = true) }

            val playerId = repository.getDbPlayerInfo()?.playerId

            useCase.incomingRoomInfoPlayers(roomNo) { roomInfoPlayersOutgoing ->
                setState {
                    copy(
                        loading = false,
                        roomInfoPlayers = roomInfoPlayersOutgoing.roomInfoPlayers,
                        isRoleHead = useCase.isValidateRoleHead(
                            playerId,
                            roomInfoPlayersOutgoing.roomInfoPlayers
                        )
                    )
                }
            }
        }
    }

    fun incomingRoomInfoTegMulti(roomNo: String?) {
        launch {
            useCase.incomingRoomInfoTegMulti(roomNo) { roomInfoTegMultiOutgoing ->
                listener?.roomInfoTegMultiResponse(roomInfoTegMultiOutgoing)
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

//            when (val resource = useCase.callMultiItemCollection(request)) {
//                is Resource.Error -> setError(resource)
//            }

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

    private fun callChangeTeam(team: String) {
        launch {
            setState { copy(loading = true) }

            when (val resource = useCase.callChangeTeam(team)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    private fun callChangeGoTeg() {
        launch {
            setState { copy(loading = true) }

            when (val resource = useCase.callChangeGoTeg(state.value?.roomInfoPlayers)) {
                is Resource.Success -> _goTegMultiEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun process(event: RoomInfoViewEvent) {
        launch {
            channel.send(event)
        }
    }

    init {
        channel
            .asFlow()
            .onEach { event ->
                when (event) {
                    is RoomInfoViewEvent.GoTeg -> callChangeGoTeg()
                    is RoomInfoViewEvent.TeamA -> callChangeTeam(TegConstant.TEAM_A)
                    is RoomInfoViewEvent.TeamB -> callChangeTeam(TegConstant.TEAM_B)
                }
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(viewModelScope)
    }

}
