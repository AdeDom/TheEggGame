package com.adedom.teg.presentation.multi

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.presentation.usercase.MultiUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MultiViewModel(
    private val useCase: MultiUseCase,
) : BaseViewModel<MultiViewState>(MultiViewState()) {

    var listener: TegMultiPlayerListener? = null

    fun callTimerTegMultiPlayer() {
        launch {
            when (val resource = useCase.callTimerTegMultiPlayer(listener)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

}
