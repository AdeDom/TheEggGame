package com.adedom.teg.presentation.single

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.ItemCollectionRequest
import com.adedom.teg.presentation.usercase.SingleUseCase
import kotlinx.coroutines.launch

class SingleViewModel(
    private val useCase: SingleUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<SingleState>(SingleState()) {

    suspend fun getDbPlayerInfo(): PlayerInfoEntity? {
        return repository.getDbPlayerInfo()
    }

    fun callItemCollection() {
        launch {
            setState { copy(loading = true) }

            // TODO: 22/10/2563 mock item
            val request = ItemCollectionRequest(
                itemId = 999,
                qty = 500,
                latitude = 13.5231001,
                longitude = 100.7517565,
            )

            when (val resource = useCase.callItemCollection(request)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
