package com.adedom.theegggame.ui.dialogs.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.JsonResponse
import com.adedom.theegggame.data.repositories.PlayerRepository

class ChangePasswordDialogViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun updatePassword(
        playerId: String,
        oldPassword: String,
        newPassword: String
    ): LiveData<JsonResponse> {
        return repository.updatePassword(playerId, oldPassword, newPassword)
    }
}
