package com.adedom.theegggame.util

import androidx.lifecycle.ViewModel
import com.adedom.theegggame.data.networks.BaseApi
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.networks.PlayerApi
import com.adedom.theegggame.data.networks.SingleApi
import com.adedom.theegggame.data.repositories.BaseRepository
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.data.repositories.PlayerRepository
import com.adedom.theegggame.data.repositories.SingleRepository

abstract class BaseViewModel : ViewModel() {
    val baseRepository by lazy { BaseRepository(BaseApi.invoke()) }
    val playerRepository by lazy { PlayerRepository(PlayerApi.invoke()) }
    val multiRepository by lazy { MultiRepository(MultiApi.invoke()) }
    val singleRepository by lazy { SingleRepository(SingleApi.invoke()) }
}