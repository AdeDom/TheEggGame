package com.adedom.theegggame.ui.dialogs.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adedom.theegggame.data.repositories.PlayerRepository

@Suppress("UNCHECKED_CAST")
class RankDialogFactory(private val repository: PlayerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RankDialogViewModel(repository) as T
    }
}
