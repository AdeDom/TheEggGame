package com.adedom.teg.presentation.endteggame

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.presentation.usercase.EndTegGameUseCase
import kotlinx.coroutines.launch

class EndTegGameViewModel(
    private val useCase: EndTegGameUseCase
) : BaseViewModel<EndTegGameViewState>(EndTegGameViewState()) {

    fun callFetchMultiPlayerEndTeg() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase.callFetchMultiPlayerEndTeg()) {
                is Resource.Success -> {
                    val endGame = resource.data.endGame
                    setState {
                        copy(
                            isBonusEndGame = endGame?.isBonusEndGame ?: false,
                            resultTeamA = endGame?.resultTeamA,
                            resultTeamB = endGame?.resultTeamB,
                            scoreTeamA = endGame?.scoreTeamA,
                            scoreTeamB = endGame?.scoreTeamB,
                        )
                    }
                }
                is Resource.Error -> setError(resource)
            }

            setState { copy(isLoading = false) }
        }
    }

}
