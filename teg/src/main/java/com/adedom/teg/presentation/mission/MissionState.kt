package com.adedom.teg.presentation.mission

data class MissionState(
    val isMissionDelivery: Boolean = false,
    val isMissionSingle: Boolean = false,
    val isMissionMulti: Boolean = false,
    val loading: Boolean = false,
)
