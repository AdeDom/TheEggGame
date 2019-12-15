package com.adedom.admin.ui.player

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.utility.OnAttachListener
import com.adedom.utility.recyclerVertical
import com.adedom.utility.setToolbar
import kotlinx.android.synthetic.main.activity_base.*

class PlayerActivity : BaseActivity<PlayerActivityViewModel>(), OnAttachListener {

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

    private fun fetchPlayers(search: String = "") {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getPlayers(search).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            mAdapter.setList(it)
        })
    }

    override fun onAttach(search: String) {
        fetchPlayers(search)
    }

}
