package com.adedom.theegggame.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.theegggame.ui.viewmodels.RegisterDialogViewModel

@Suppress("UNCHECKED_CAST")
class RegisterPlayerDialogFactory(private val repository: PlayerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterDialogViewModel(repository) as T
    }
}
