package com.adedom.theegggame.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.RoomRepository
import com.adedom.theegggame.ui.viewmodels.RoomActivityViewModel

@Suppress("UNCHECKED_CAST")
class RoomActivityFactory(private val repository: RoomRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomActivityViewModel(
            repository
        ) as T
    }
}
