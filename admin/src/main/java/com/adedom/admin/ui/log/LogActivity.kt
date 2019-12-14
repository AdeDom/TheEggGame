package com.adedom.admin.ui.log

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.data.networks.BaseApi
import com.adedom.admin.data.repositories.BaseRepository
import com.adedom.utility.recyclerVertical
import com.adedom.utility.util.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*

class LogActivity : BaseActivity<LogActivityViewModel>() {

    private lateinit var mAdapter: LogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        factory.viewModel = { LogActivityViewModel(BaseRepository(BaseApi.invoke())) }
        viewModel = ViewModelProviders.of(this, factory).get(LogActivityViewModel::class.java)

        init()

        fetchLogs()
    }

    private fun init() {
        mAdapter = LogAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.also {
            it.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
            it.setOnRefreshListener {
                fetchLogs()
            }
        }
    }

    private fun fetchLogs() {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getLogs().observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            mAdapter.setList(it)
        })
    }
}
