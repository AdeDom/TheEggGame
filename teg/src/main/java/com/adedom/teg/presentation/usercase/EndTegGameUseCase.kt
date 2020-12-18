package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.MultiPlayerEndGameResponse

interface EndTegGameUseCase {

    suspend fun callFetchMultiPlayerEndTeg(): Resource<MultiPlayerEndGameResponse>

}
