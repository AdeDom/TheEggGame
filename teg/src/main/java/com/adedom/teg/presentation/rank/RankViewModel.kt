package com.adedom.teg.presentation.rank

import com.adedom.teg.base.BaseViewModel
import com.adedom.teg.domain.Resource
import com.adedom.teg.domain.repository.DefaultTegRepository
import com.adedom.teg.models.response.PlayerInfo
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

@ExperimentalStdlibApi
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

    fun callFetchRankPlayers() {
        launch {
            setState { copy(loading = true) }

            when (val resource = repository.callFetchRankPlayers()) {
                is Resource.Success -> setState {
                    copy(
                        rankPlayersInitial = resource.data.rankPlayers,
                        rankPlayers = resource.data.rankPlayers,
                    )
                }
                is Resource.Error -> setError(resource)
            }

            setState { copy(loading = false) }
        }
    }

    fun searchByName(search: String) {
        val rankPlayers = mutableListOf<PlayerInfo>()
        state.value?.rankPlayers?.forEach { playerInfo ->
            if (playerInfo.name.lowercase().contains(search.lowercase())) {
                rankPlayers.add(playerInfo)
            }
        }
        setState { copy(rankPlayers = rankPlayers) }
    }

    init {
        channel
            .asFlow()
            .onEach { event ->
                when (event) {
                    RankViewEvent.Ten -> setState {
                        copy(rankPlayers = rankPlayersInitial.take(TegConstant.RANK_LIMIT_TEN))
                    }
                    RankViewEvent.Fifty -> setState {
                        copy(rankPlayers = rankPlayersInitial.take(TegConstant.RANK_LIMIT_FIFTY))
                    }
                    RankViewEvent.Hundred -> setState {
                        copy(rankPlayers = rankPlayersInitial.take(TegConstant.RANK_LIMIT_HUNDRED))
                    }
                }
            }
            .catch { e ->
                setError(Resource.Error(e))
            }
            .launchIn(this)
    }

}
