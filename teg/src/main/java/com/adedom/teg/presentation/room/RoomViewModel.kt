package com.adedom.teg.presentation.room

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.repository.DefaultTegRepository
import kotlinx.coroutines.launch

class RoomViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<RoomViewState>(RoomViewState()) {

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

}
