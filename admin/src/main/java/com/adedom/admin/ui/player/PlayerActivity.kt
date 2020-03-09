package com.adedom.admin.ui.player

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.admin.util.KEY_STRING
import com.adedom.library.extension.recyclerVertical
import com.adedom.library.extension.setSwipeRefresh
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity<PlayerActivityViewModel>() {

    private lateinit var mAdapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        viewModel = ViewModelProvider(this).get(PlayerActivityViewModel::class.java)

        init()

        fetchPlayers()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report1), true)

        mAdapter = PlayerAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.setSwipeRefresh { fetchPlayers() }

        mFloatingActionButton.setOnClickListener {
            PlayerDialog().show(supportFragmentManager, null)
        }
    }

    private fun fetchPlayers(
        name: String = KEY_STRING,
        levelStart: Int = 1,
        levelEnd: Int = 99,
        online: Boolean = true,
        offline: Boolean = true,
        male: Boolean = true,
        female: Boolean = true
    ) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getPlayers(name, levelStart, levelEnd, online, offline, male, female)
            .observe(this, Observer {
                mSwipeRefreshLayout.isRefreshing = false
                if (it.isEmpty()) baseContext.toast(
                    R.string.search_data_not_found,
                    Toast.LENGTH_LONG
                )
                mAdapter.setList(it)
            })
    }

    override fun onAttach() {
        fetchPlayers(
            PlayerActivityViewModel.name,
            PlayerActivityViewModel.spinnerIndexStart.plus(1),
            PlayerActivityViewModel.spinnerIndexEnd.plus(1),
            PlayerActivityViewModel.isCheckOnline,
            PlayerActivityViewModel.isCheckOffline,
            PlayerActivityViewModel.isCheckMale,
            PlayerActivityViewModel.isCheckFemale
        )

    }

}
