package com.adedom.theegggame.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.repositories.PlayerRepository
import com.adedom.theegggame.viewmodels.LoginActivityViewModel

@Suppress("UNCHECKED_CAST")
class LoginActivityFactory(
    private val repository: PlayerRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginActivityViewModel(repository) as T
    }
}
