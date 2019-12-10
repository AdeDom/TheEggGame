package com.adedom.theegggame.ui.dialogs.createroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.MultiRepository

class CreateRoomDialogViewModel(private val repository: MultiRepository) : ViewModel() {

    fun insertRoom(
        name: String,
        people: String,
        playerId: String,
        date: String,
        time: String
    ): LiveData<JsonResponse> {
        return repository.insertRoom(name, people, playerId, date, time)
    }
}
