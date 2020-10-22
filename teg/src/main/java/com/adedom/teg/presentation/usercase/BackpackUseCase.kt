package com.adedom.teg.presentation.usercase

import com.adedom.teg.domain.Resource
import com.adedom.teg.models.response.BackpackResponse

interface BackpackUseCase {

    suspend fun callFetchItemCollection(): Resource<BackpackResponse>

}
