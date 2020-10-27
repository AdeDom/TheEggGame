package com.adedom.teg.presentation.single

sealed class SingleViewEvent {
    object CallItemCollection : SingleViewEvent()
    object BackpackFragment : SingleViewEvent()
}
