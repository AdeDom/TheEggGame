package com.adedom.teg.presentation.endteggame

data class EndTegGameViewState(
    val isBonusEndGame: Boolean = false,
    val resultTeamA: String? = "",
    val resultTeamB: String? = "",
    val scoreTeamA: Int? = 0,
    val scoreTeamB: Int? = 0,
    val isLoading: Boolean = false,
)
