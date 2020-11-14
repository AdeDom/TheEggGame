package com.adedom.teg.presentation.single

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.ItemCollectionRequest
import com.adedom.teg.presentation.usercase.SingleUseCase
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SingleViewModel(
    private val useCase: SingleUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<SingleViewState>(SingleViewState()) {

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    fun incomingSinglePeopleAll() {
        launch {
            repository.incomingSinglePeopleAll { roomPeopleAllOutgoing ->
                setState { copy(peopleAll = roomPeopleAllOutgoing.peopleAll) }
            }
            incomingSinglePeopleAll()
        }
    }

    fun setStateLatLng(latitude: Double, longitude: Double) {
        setState { copy(latLng = SingleViewState.Latlng(latitude, longitude)) }
    }

    fun setStateBitmap(bitmap: Bitmap) {
        setState { copy(bitmap = bitmap) }
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

    fun callChangeCurrentModeSingle() {
        launch {
            when (val resource = repository.callChangeCurrentMode(TegConstant.PLAY_MODE_SINGLE)) {
                is Resource.Error -> setError(resource)
            }
        }
    }

    init {
        launch {
            val playerInfo = repository.getDbPlayerInfo()

            setState {
                copy(
                    name = playerInfo?.name.orEmpty(),
                    level = playerInfo?.level ?: 0
                )
            }
        }
    }

}
