package com.adedom.teg.presentation.mission

data class MissionViewState(
    val isMissionDelivery: Boolean = false,
    val isMissionDeliveryCompleted: Boolean = false,
    val isMissionSingle: Boolean = false,
    val isMissionSingleCompleted: Boolean = false,
    val isMissionMulti: Boolean = false,
    val isMissionMultiCompleted: Boolean = false,
    val loading: Boolean = false,
)
