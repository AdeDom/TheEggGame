package com.adedom.theegggame.ui.multi.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.MultiRepository

@Suppress("UNCHECKED_CAST")
class RoomActivityFactory(private val repository: MultiRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomActivityViewModel(repository) as T
    }
}
