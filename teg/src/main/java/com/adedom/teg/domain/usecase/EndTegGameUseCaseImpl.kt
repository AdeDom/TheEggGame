package com.adedom.teg.domain.usecase

import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.MultiPlayerEndGameResponse
import com.adedom.teg.presentation.usercase.EndTegGameUseCase

class EndTegGameUseCaseImpl(private val repository: DefaultTegRepository) : EndTegGameUseCase {

    override suspend fun callFetchMultiPlayerEndTeg(): Resource<MultiPlayerEndGameResponse> {
        return repository.callFetchMultiPlayerEndTeg()
    }

}
