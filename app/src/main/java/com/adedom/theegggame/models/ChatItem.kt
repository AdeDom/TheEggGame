package com.adedom.theegggame.models

data class ChatItem(
    val playerId: String,
    val time: String,
    val message: String,
    val image: String,
    val type: String
)