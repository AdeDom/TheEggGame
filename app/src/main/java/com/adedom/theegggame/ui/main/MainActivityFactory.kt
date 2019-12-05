package com.adedom.theegggame.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.PlayerRepository

@Suppress("UNCHECKED_CAST")
class MainActivityFactory(private val repository: PlayerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}
