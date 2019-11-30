package com.adedom.theegggame.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.models.Player
import com.adedom.theegggame.repositories.PlayerRepository
import kotlinx.coroutines.Job

class LoginActivityViewModel(
    private val repository: PlayerRepository
) : ViewModel() {

    private lateinit var job: Job

    private val _playerId = MutableLiveData<Player>()

    val playerId: LiveData<Player> = _playerId

    fun getPlayerId(username: String, password: String) {
        job = ioThenMain(
            { repository.getPlayerId(username, password) },
            { _playerId.value = it }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}