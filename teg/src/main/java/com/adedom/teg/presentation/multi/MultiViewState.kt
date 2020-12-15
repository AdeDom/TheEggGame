package com.adedom.teg.presentation.multi

import com.adedom.teg.data.models.MultiItemDb

data class MultiViewState(
    val multiItems: List<MultiItemDb> = emptyList(),
    val isLoading: Boolean = false,
)
