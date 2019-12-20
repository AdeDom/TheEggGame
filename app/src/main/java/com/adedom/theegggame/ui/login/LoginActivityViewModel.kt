package com.adedom.theegggame.ui.login

import com.adedom.theegggame.util.BaseViewModel

class LoginActivityViewModel : BaseViewModel() {

    fun getPlayerIdLogin(username: String, password: String) =
        playerRepository.getPlayerIdLogin(username, password)
}