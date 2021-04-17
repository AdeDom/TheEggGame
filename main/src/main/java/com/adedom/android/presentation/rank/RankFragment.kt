package com.adedom.android.presentation.rank

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.clicks
import com.adedom.android.util.setVisibility
import com.adedom.teg.presentation.rank.RankViewEvent
import com.adedom.teg.presentation.rank.RankViewModel
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalStdlibApi
@FlowPreview
@ExperimentalCoroutinesApi
class RankFragment : BaseFragment(R.layout.fragment_rank) {

    private val viewModel by viewModel<RankViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adt = RankAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adt
        }

        viewModel.attachFirstTime.observe {
            viewModel.callFetchRankPlayers()
        }

        viewModel.state.observe { state ->
            animationViewLoading.setVisibility(state.loading)

            adt.submitList(state.rankPlayers)
        }

        viewModel.error.observeError()

        etSearch.addTextChangedListener { viewModel.searchByName(it.toString()) }

        viewEventFlow().observe { viewModel.process(it) }
    }

    private fun viewEventFlow(): Flow<RankViewEvent> {
        return merge(
            etSearch.clicks().map { RankViewEvent.Search },
            btRank10.clicks().map { RankViewEvent.Ten },
            btRank50.clicks().map { RankViewEvent.Fifty },
            btRank100.clicks().map { RankViewEvent.Hundred },
        )
    }

}
