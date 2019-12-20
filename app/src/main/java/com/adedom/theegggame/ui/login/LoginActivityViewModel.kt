package com.adedom.theegggame.ui.login

import com.adedom.theegggame.util.BaseViewModel

class LoginActivityViewModel : BaseViewModel() {

    fun getPlayerIdLogin(username: String, password: String) =
        playerRepository.getPlayerIdLogin(username, password)

    fun insertPlayer(
        username: String,
        password: String,
        name: String,
        image: String,
        date: String,
        time: String
    ) = playerRepository.insertPlayer(username, password, name, image, date, time)
}