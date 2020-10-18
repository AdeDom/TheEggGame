package com.adedom.teg.presentation.imageprofile

data class ImageProfileState(
    val imageUri: String = "",
    val isImageUri: Boolean = false,
    val loading: Boolean = false,
)
