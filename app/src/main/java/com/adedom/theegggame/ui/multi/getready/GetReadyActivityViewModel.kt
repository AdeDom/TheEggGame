package com.adedom.theegggame.ui.multi.getready

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.MultiRepository

class GetReadyActivityViewModel(private val repository: MultiRepository) : ViewModel() {

    fun deletePlayer(roomNo: String, playerId: String): LiveData<JsonResponse> {
        return repository.deletePlayer(roomNo, playerId)
    }

}
