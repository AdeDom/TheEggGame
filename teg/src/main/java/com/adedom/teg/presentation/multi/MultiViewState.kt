package com.adedom.teg.presentation.multi

import com.adedom.teg.data.models.MultiItemDb
import com.adedom.teg.models.TegLatLng

data class MultiViewState(
    val scoreTeamA: Int? = 0,
    val scoreTeamB: Int? = 0,
    val latLng: TegLatLng = TegLatLng(),
    val isValidateDistanceBetween: Boolean = false,
    val multiItems: List<MultiItemDb> = emptyList(),
    val isLoading: Boolean = false,
)
