package com.adedom.teg.presentation.rank

sealed class RankViewEvent {
    object Search : RankViewEvent()
    object Ten : RankViewEvent()
    object Fifty : RankViewEvent()
    object Hundred : RankViewEvent()
}
