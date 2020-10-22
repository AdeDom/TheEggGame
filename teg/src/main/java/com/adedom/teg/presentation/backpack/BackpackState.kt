package com.adedom.teg.presentation.backpack

import com.adedom.teg.data.models.BackpackDb

data class BackpackState(
    val backpack: BackpackDb? = null,
    val loading: Boolean = false,
)
