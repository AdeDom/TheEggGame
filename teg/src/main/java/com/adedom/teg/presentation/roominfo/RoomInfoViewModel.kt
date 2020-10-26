package com.adedom.teg.presentation.roominfo

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.presentation.usercase.MultiUseCase
import kotlinx.coroutines.launch

class RoomInfoViewModel(
    private val useCase: MultiUseCase,
) : BaseViewModel<RoomInfoViewState>(RoomInfoViewState()) {

    fun callMultiItemCollection() {
        launch {
            setState { copy(loading = true) }

            val request = MultiItemCollectionRequest(
                itemId = 999,
                qty = 500,
                latitude = 13.5231001,
                longitude = 100.7517565,
            )

            when (val resource = useCase.callMultiItemCollection(request)) {
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

}
