package com.adedom.teg.presentation.single

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.data.db.entities.PlayerInfoEntity
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.request.ItemCollectionRequest
import com.adedom.teg.presentation.usercase.SingleUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SingleViewModel(
    private val useCase: SingleUseCase,
    private val repository: DefaultTegRepository,
) : BaseViewModel<SingleViewState>(SingleViewState()) {

    private val channel = ConflatedBroadcastChannel<SingleViewEvent>()

    val getDbPlayerInfoLiveData: LiveData<PlayerInfoEntity>
        get() = repository.getDbPlayerInfoLiveData()

    private val _singleViewEvent = MutableLiveData<SingleViewEvent>()
    val singleViewEvent: LiveData<SingleViewEvent>
        get() = _singleViewEvent

    fun setStateLatLng(latitude: Double, longitude: Double) {
        setState { copy(latLng = SingleViewState.Latlng(latitude, longitude)) }
    }

    fun setStateBitmap(bitmap: Bitmap) {
        setState { copy(bitmap = bitmap) }
    }

    private fun callItemCollection() {
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

    fun process(event: SingleViewEvent) {
        launch {
            channel.send(event)
        }
    }

    init {
        channel
            .asFlow()
            .onEach { event ->
                when (event) {
                    is SingleViewEvent.CallItemCollection -> callItemCollection()
                    is SingleViewEvent.BackpackFragment -> {
                        _singleViewEvent.value = SingleViewEvent.BackpackFragment
                    }
                }
            }
            .onEach {
                Log.d("AdeDom", "Single : $it")
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(this)

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
