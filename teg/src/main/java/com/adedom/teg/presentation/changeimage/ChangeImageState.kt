package com.adedom.teg.presentation.changeimage

data class ChangeImageState(
    val imageUri: String = "",
    val isImageUri: Boolean = false,
    val loading: Boolean = false,
)
