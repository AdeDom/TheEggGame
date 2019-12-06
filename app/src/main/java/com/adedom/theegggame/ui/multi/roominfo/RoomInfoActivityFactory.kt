package com.adedom.theegggame.ui.multi.roominfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.MultiRepository

@Suppress("UNCHECKED_CAST")
class RoomInfoActivityFactory(private val repository: MultiRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomInfoActivityViewModel(repository) as T
    }
}
