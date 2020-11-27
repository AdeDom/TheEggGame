package com.adedom.teg.presentation.single

import com.adedom.teg.data.models.SingleItemDb
import com.adedom.teg.models.TegLatLng
import com.adedom.teg.models.response.PlayerInfo
import com.adedom.teg.models.websocket.SingleSuccessAnnouncementOutgoing

data class SingleViewState(
    val singleItems: List<SingleItemDb> = emptyList(),
    val peopleAll: Int = 0,
    val latLng: TegLatLng = TegLatLng(),
    val isValidateDistanceBetween: Boolean = false,
    val singleSuccessAnnouncement: SingleSuccessAnnouncementOutgoing? = null,
    val isSingleSuccessAnnouncement: Boolean = false,
    val players: List<PlayerInfo> = emptyList(),
    val loading: Boolean = false,
)
