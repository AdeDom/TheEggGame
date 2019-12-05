package com.adedom.theegggame.ui.dialogs.createroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.MultiRepository

@Suppress("UNCHECKED_CAST")
class CreateRoomDialogFactory(private val repository: MultiRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateRoomDialogViewModel(repository) as T
    }
}
