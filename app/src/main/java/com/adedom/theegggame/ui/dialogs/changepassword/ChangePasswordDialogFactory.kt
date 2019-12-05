package com.adedom.theegggame.ui.dialogs.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.PlayerRepository

@Suppress("UNCHECKED_CAST")
class ChangePasswordDialogFactory(private val repository: PlayerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChangePasswordDialogViewModel(
            repository
        ) as T
    }
}
