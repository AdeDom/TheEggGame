package com.adedom.theegggame.ui.dialogs.createroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.MultiRepository

class CreateRoomDialogViewModel(private val repository: MultiRepository) : ViewModel() {

    fun createRoom(name: String, people: String, playerId: String): LiveData<JsonResponse> {
        return repository.createRoom(name, people, playerId)
    }

}
