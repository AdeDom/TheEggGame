package com.adedom.admin.ui.player

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_base.*

class PlayerActivity : BaseActivity<PlayerActivityViewModel>() {

    private lateinit var mAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PlayerActivityViewModel::class.java)

        init()

        fetchPlayers()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report1))

        mAdapter = PlayerAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.setOnRefreshListener { fetchPlayers() }

        mFloatingActionButton.setOnClickListener {
            PlayerDialog().show(supportFragmentManager, null)
        }
    }

    private fun fetchPlayers(
        search: String = "",
        level: String = "",
        online: Boolean = true,
        offline: Boolean = true
    ) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getPlayers(search, level, online, offline).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }

    override fun onAttach() {
        fetchPlayers(search, spinnerLevel.toString(), isCheckOnline, isCheckOffline)
    }

}
