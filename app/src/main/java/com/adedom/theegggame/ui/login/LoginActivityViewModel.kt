package com.adedom.theegggame.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.PlayerRepository

class LoginActivityViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun getPlayerIdLogin(username: String, password: String): LiveData<JsonResponse> {
        return repository.getPlayerIdLogin(username, password)
    }
}