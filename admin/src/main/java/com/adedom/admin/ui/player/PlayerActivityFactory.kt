package com.adedom.admin.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.admin.data.repositories.BaseRepository

@Suppress("UNCHECKED_CAST")
class PlayerActivityFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerActivityViewModel(repository) as T
    }
}
