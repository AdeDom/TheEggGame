package com.adedom.teg.presentation.usercase

interface MainUseCase {

    suspend fun fetchPlayerInfo(): Boolean = false

    suspend fun signOut(): Boolean = false

}
