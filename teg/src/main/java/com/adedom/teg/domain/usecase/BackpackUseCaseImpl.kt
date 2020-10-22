package com.adedom.teg.domain.usecase

import com.adedom.teg.data.db.entities.BackpackEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.BackpackResponse
import com.adedom.teg.presentation.usercase.BackpackUseCase

class BackpackUseCaseImpl(
    private val repository: DefaultTegRepository,
) : BackpackUseCase {

    override suspend fun callFetchItemCollection(): Resource<BackpackResponse> {
        val resource = repository.callFetchItemCollection()

        when (resource) {
            is Resource.Success -> {
                if (resource.data.success) {
                    val backpack = resource.data.backpack
                    val backpackEntity = BackpackEntity(
                        eggI = backpack?.eggI,
                        eggII = backpack?.eggII,
                        eggIII = backpack?.eggIII,
                    )
                    repository.saveBackpack(backpackEntity)
                }
            }
        }

        return resource
    }

}
