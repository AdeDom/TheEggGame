package com.adedom.teg.presentation.roominfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.model.ValidateRoleRoomInfo
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

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

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

                if (state.value?.roomNo == roomInfoPlayersOutgoing.roomNo) {
                    setState { copy(roomInfoPlayers = roomInfoPlayersOutgoing.roomInfoPlayers) }

                    val validateRoleRoomInfo = ValidateRoleRoomInfo(
                        playerId = state.value?.playerId,
                        roomInfoPlayers = roomInfoPlayersOutgoing.roomInfoPlayers
                    )
                    setState { copy(isRoleHead = useCase.isValidateRoleHead(validateRoleRoomInfo)) }
                }
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

            val validateRoleRoomInfo = ValidateRoleRoomInfo(
                playerId = state.value?.playerId,
                roomInfoPlayers = state.value?.roomInfoPlayers,
            )
            when (val resource = useCase.callChangeGoTeg(validateRoleRoomInfo)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun setStateRoomNo(roomNo: String?) {
        setState { copy(roomNo = roomNo) }
    }

    fun setStatePlayerId(playerId: String?) {
        setState { copy(playerId = playerId) }
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
