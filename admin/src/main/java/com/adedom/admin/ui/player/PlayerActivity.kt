package com.adedom.admin.ui.player

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.admin.util.OnAttachListener
import com.adedom.utility.*
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
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }

    override fun onAttach() {
        fetchPlayers(search)

        Log.d(TAG, ">>$search")
        Log.d(TAG, ">>$spinnerLevel")
        Log.d(TAG, ">>$isCheckOnline")
        Log.d(TAG, ">>$isCheckOffline")
        Log.d(TAG, ">>$dateBegin")
        Log.d(TAG, ">>$dateEnd")
        Log.d(TAG, ">>$timeBegin")
        Log.d(TAG, ">>$timeEnd")
    }

}
