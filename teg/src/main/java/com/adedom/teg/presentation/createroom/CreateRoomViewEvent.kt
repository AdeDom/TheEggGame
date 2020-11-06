package com.adedom.teg.presentation.createroom

sealed class CreateRoomViewEvent {
    object ArrowLeft : CreateRoomViewEvent()
    object ArrowRight : CreateRoomViewEvent()
}
