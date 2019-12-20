package com.adedom.admin.ui.player

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.utility.extension.recyclerVertical
import com.adedom.utility.extension.setToolbar
import com.adedom.utility.extension.toast
import com.adedom.utility.isCheckOffline
import com.adedom.utility.isCheckOnline
import com.adedom.utility.name
import com.adedom.utility.spinnerLevel
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity<PlayerActivityViewModel>() {

    private lateinit var mAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        viewModel = ViewModelProviders.of(this).get(PlayerActivityViewModel::class.java)

        init()

        fetchPlayers()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report1))

        mAdapter = PlayerAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.apply {
            setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
            setOnRefreshListener { fetchPlayers() }
        }

        mFloatingActionButton.setOnClickListener {
            PlayerDialog().show(supportFragmentManager, null)
        }
    }

    private fun fetchPlayers(
        name: String = "",
        level: String = "",
        online: Boolean = true,
        offline: Boolean = true
    ) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getPlayers(name, level, online, offline).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }

    override fun onAttach() {
        fetchPlayers(name, spinnerLevel.toString(), isCheckOnline, isCheckOffline)
    }

}
