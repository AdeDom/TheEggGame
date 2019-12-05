package com.adedom.theegggame.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.PlayerRepository

@Suppress("UNCHECKED_CAST")
class LoginActivityFactory(private val repository: PlayerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginActivityViewModel(repository) as T
    }
}
