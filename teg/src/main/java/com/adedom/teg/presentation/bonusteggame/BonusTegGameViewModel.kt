package com.adedom.teg.presentation.bonusteggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.models.request.MultiItemCollectionRequest
import com.adedom.teg.models.response.BaseResponse
import com.adedom.teg.presentation.usercase.BonusTegGameUseCase
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.launch

class BonusTegGameViewModel(
    private val useCase: BonusTegGameUseCase,
) : BaseViewModel<BonusTegGameViewState>(BonusTegGameViewState()) {

    private val _bonusTegGameEvent = MutableLiveData<BaseResponse>()
    val bonusTegGameEvent: LiveData<BaseResponse>
        get() = _bonusTegGameEvent

    fun callBonusTegGame() {
        launch {
            setState { copy(isLoading = true, isRotateBonusCompleted = false) }

            val request = MultiItemCollectionRequest(
                itemId = TegConstant.ITEM_LEVEL,
                qty = state.value?.bonusPoint,
                latitude = 0.0,
                longitude = 0.0,
            )
            when (val resource = useCase.callMultiItemCollection(request)) {
                is Resource.Success -> _bonusTegGameEvent.value = resource.data
                is Resource.Error -> setError(resource)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun setStateAnimationEnd() {
        val newDir = state.value?.newDir ?: 0F
        val degree = 360 - newDir % 360
        setState {
            copy(
                bonusPoint = useCase.calBonusTegGame(degree) + bonusPoint,
                countClickBonus = countClickBonus.plus(1),
                isRotateBonusCompleted = countClickBonus == 2,
                lastDir = newDir
            )
        }
    }

    fun setStateNewDir() {
        setState { copy(newDir = (0..3600).random().plus(1800).toFloat()) }
    }

}
