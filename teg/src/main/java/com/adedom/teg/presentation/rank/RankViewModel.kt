package com.adedom.teg.presentation.rank

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
) : BaseViewModel<RankViewState>(RankViewState()) {

    private val channel = BroadcastChannel<RankViewEvent>(Channel.BUFFERED)

    fun process(event: RankViewEvent) {
        launch {
            channel.send(event)
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
            .onEach { event ->
                when (event) {
                    RankViewEvent.Ten -> setState { copy(limit = TegConstant.RANK_LIMIT_TEN) }
                    RankViewEvent.Fifty -> setState { copy(limit = TegConstant.RANK_LIMIT_FIFTY) }
                    RankViewEvent.Hundred -> setState { copy(limit = TegConstant.RANK_LIMIT_HUNDRED) }
                }
            }
            .onEach {
                callFetchRankPlayers(
                    search = state.value?.search.orEmpty(),
                    limit = state.value?.limit ?: 0,
                )
            }
            .onEach {
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(this)
    }

}
