package com.adedom.theegggame.ui.dialogs.changepassword

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.PlayerRepository

class ChangePasswordDialogViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun updatePassword(playerId: String, oldPassword: String, newPassword: String) =
        repository.updatePassword(playerId, oldPassword, newPassword)

}
