package com.adedom.teg.presentation.single

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.TegLatLng
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

    fun incomingSingleItemAround(latLng: TegLatLng) {
        launch {
            setState { copy(loading = true) }

            useCase.incomingSingleItem(latLng) { singleItemAroundOutgoing ->
                setState {
                    copy(
                        singleItems = singleItemAroundOutgoing.singleItems,
                        loading = false
                    )
                }
            }
            incomingSingleItemAround(latLng)
        }
    }

    fun setStateLatLng(latLng: TegLatLng) {
        setState {
            copy(
                latLng = latLng,
                isValidateDistanceBetween = useCase.isValidateDistanceBetween(
                    latLng,
                    state.value?.singleItems
                )
            )
        }
    }

    fun setStateBitmap(bitmap: Bitmap) {
        setState { copy(bitmap = bitmap) }
    }

    fun callSingleItemCollection() {
        launch {
            setState { copy(loading = true, isValidateDistanceBetween = false) }

            val singleId = useCase.getSingleItemId(state.value?.latLng, state.value?.singleItems)

            when (val resource = useCase.callSingleItemCollection(singleId)) {
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
