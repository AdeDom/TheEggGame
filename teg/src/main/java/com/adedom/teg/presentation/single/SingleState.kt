package com.adedom.teg.presentation.single

import com.adedom.teg.data.models.BackpackDb

data class SingleState(
    val backpack: BackpackDb? = null,
    val loading: Boolean = false,
)
