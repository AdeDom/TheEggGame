package com.adedom.teg.presentation.rank

import com.adedom.teg.models.response.PlayerInfo

data class RankState(
    val search: String = "",
    val limit: Int = 0,
    val rankPlayers: List<PlayerInfo> = emptyList(),
    val loading: Boolean = false,
)
