package com.adedom.theegggame.ui.dialogs.rank

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.repositories.PlayerRepository

class RankDialogViewModel(private val repository: PlayerRepository) : ViewModel() {

    fun getPlayerRank(search: String, limit: String) = repository.getPlayerRank(search, limit)
}
