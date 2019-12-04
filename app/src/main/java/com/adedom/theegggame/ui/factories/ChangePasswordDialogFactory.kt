package com.adedom.theegggame.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.RetrofitRepository
import com.adedom.theegggame.ui.viewmodels.ChangePasswordDialogViewModel

@Suppress("UNCHECKED_CAST")
class ChangePasswordDialogFactory(private val repository: RetrofitRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChangePasswordDialogViewModel(repository) as T
    }
}
