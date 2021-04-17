package com.adedom.teg.presentation.rank

import com.adedom.teg.models.response.PlayerInfo

data class RankViewState(
    val rankPlayersInitial: List<PlayerInfo> = emptyList(),
    val rankPlayers: List<PlayerInfo> = emptyList(),
    val loading: Boolean = false,
)
