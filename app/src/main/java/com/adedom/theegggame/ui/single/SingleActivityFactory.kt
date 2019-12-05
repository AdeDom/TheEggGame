package com.adedom.theegggame.ui.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.SingleRepository

@Suppress("UNCHECKED_CAST")
class SingleActivityFactory(private val repository: SingleRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SingleActivityViewModel(
            repository
        ) as T
    }
}
