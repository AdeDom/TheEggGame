package com.adedom.theegggame.ui.multi.multi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.MultiRepository

@Suppress("UNCHECKED_CAST")
class MultiActivityFactory(private val repository: MultiRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MultiActivityViewModel(repository) as T
    }
}
