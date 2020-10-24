package com.adedom.teg.presentation.usercase

interface MainUseCase {

    suspend fun fetchPlayerInfo(): Boolean = false

    suspend fun callPlayerStateOffline()

    suspend fun callLogActiveOff()

}
