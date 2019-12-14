package com.adedom.admin.ui.player

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.data.networks.BaseApi
import com.adedom.admin.data.repositories.BaseRepository
import com.adedom.utility.recyclerVertical
import com.adedom.utility.util.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity<PlayerActivityViewModel>() {

    private lateinit var mAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        factory.viewModel = { PlayerActivityViewModel(BaseRepository(BaseApi.invoke())) }
        viewModel = ViewModelProviders.of(this, factory).get(PlayerActivityViewModel::class.java)

        init()

        fetchPlayers()

    }

    private fun init() {
        mAdapter = PlayerAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.also {
            it.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
            it.setOnRefreshListener {
                fetchPlayers()
            }
        }
    }

    private fun fetchPlayers(search: String = "") {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getPlayers(search).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            mAdapter.setList(it)
        })
    }

}
