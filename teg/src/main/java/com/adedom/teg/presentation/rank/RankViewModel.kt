package com.adedom.teg.presentation.rank

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.util.TegConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class RankViewModel(
    private val repository: DefaultTegRepository,
) : BaseViewModel<RankState>(RankState()) {

    private val channel = BroadcastChannel<RankAction>(Channel.BUFFERED)

    fun sendAction(action: RankAction) {
        launch {
            channel.send(action)
        }
    }

    fun callFetchRankPlayers(search: String = "", limit: Int = TegConstant.RANK_LIMIT_HUNDRED) {
        launch {
            setState { copy(loading = true) }

            when (val resource = repository.callFetchRankPlayers(search, limit)) {
                is Resource.Success -> setState { copy(rankPlayers = resource.data.rankPlayers) }
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun setStateSearch(search: String) {
        setState { copy(search = search) }
    }

    init {
        channel
            .asFlow()
            .onEach { action ->
                when (action) {
                    RankAction.TEN -> setState { copy(limit = TegConstant.RANK_LIMIT_TEN) }
                    RankAction.FIFTY -> setState { copy(limit = TegConstant.RANK_LIMIT_FIFTY) }
                    RankAction.HUNDRED -> setState { copy(limit = TegConstant.RANK_LIMIT_HUNDRED) }
                }
            }
            .onEach {
                callFetchRankPlayers(
                    search = state.value?.search.orEmpty(),
                    limit = state.value?.limit ?: 0,
                )
            }
            .onEach {
                Log.d("AdeDom", "Rank : ${state.value?.search}, ${state.value?.limit}")
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(viewModelScope)
    }

}
