package com.adedom.teg.presentation.bonusteggame

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.presentation.usercase.BonusTegGameUseCase

class BonusTegGameViewModel(
    private val useCase: BonusTegGameUseCase,
) : BaseViewModel<BonusTegGameViewState>(BonusTegGameViewState()) {



}
