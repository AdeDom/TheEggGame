package com.adedom.theegggame.ui.dialogs.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.data.repositories.PlayerRepository

class RankDialogViewModel(private val repository: PlayerRepository) : ViewModel() { // 2/12/19

    fun getPlayerRank(search: String, limit: String): LiveData<List<Player>> {
        return repository.getPlayerRank(search, limit)
    }
}
