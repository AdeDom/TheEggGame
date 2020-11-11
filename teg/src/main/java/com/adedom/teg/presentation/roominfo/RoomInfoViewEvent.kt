package com.adedom.teg.presentation.roominfo

sealed class RoomInfoViewEvent {
    object GoTeg : RoomInfoViewEvent()
    object TeamA : RoomInfoViewEvent()
    object TeamB : RoomInfoViewEvent()
}
