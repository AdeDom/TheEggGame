package com.adedom.theegggame.ui.login

import com.adedom.theegggame.util.BaseViewModel

class LoginActivityViewModel : BaseViewModel() {

    fun getPlayerId(username: String, password: String) =
        playerRepository.getPlayerId(username, password)

    fun register(
        username: String,
        password: String,
        name: String,
        image: String,
        date: String,
        time: String,
        gender: String
    ) = playerRepository.insertPlayer(username, password, name, image, date, time, gender)
}