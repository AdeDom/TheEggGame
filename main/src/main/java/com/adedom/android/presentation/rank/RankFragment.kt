package com.adedom.android.presentation.rank

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.adedom.android.R
import com.adedom.android.base.BaseFragment
import com.adedom.android.util.clicks
import com.adedom.teg.presentation.rank.RankAction
import com.adedom.teg.presentation.rank.RankViewModel
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            progressBar.visibility = if (state.loading) View.VISIBLE else View.INVISIBLE

            adt.setList(state.rankPlayers)
        }

        viewModel.error.observeError()

        etSearch.addTextChangedListener { viewModel.setStateSearch(it.toString()) }

        actionFlow().observe { viewModel.sendAction(it) }
    }

    private fun actionFlow(): Flow<RankAction> {
        return merge(
            etSearch.clicks().map { RankAction.SEARCH },
            btRank10.clicks().map { RankAction.TEN },
            btRank50.clicks().map { RankAction.FIFTY },
            btRank100.clicks().map { RankAction.HUNDRED },
        )
    }

}
