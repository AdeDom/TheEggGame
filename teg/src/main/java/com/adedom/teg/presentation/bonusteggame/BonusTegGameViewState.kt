package com.adedom.teg.presentation.bonusteggame

data class BonusTegGameViewState(
    val newDir: Float = 0F,
    val lastDir: Float = 0F,
    val bonusPoint: Int = 0,
    val countClickBonus: Int = 0,
    val isRotateBonusCompleted: Boolean = false,
    val isLoading: Boolean = false,
)
