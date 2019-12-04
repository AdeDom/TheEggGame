package com.adedom.theegggame.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.repositories.RoomRepository

class RoomActivityViewModel(private val repository: RoomRepository) : ViewModel() {

    fun getRooms(): LiveData<List<Room>> = repository.getRooms()

}
