package com.adedom.admin.ui.logs

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.utility.recyclerVertical
import com.adedom.utility.setToolbar
import com.adedom.utility.toast
import kotlinx.android.synthetic.main.activity_base.*

class LogsActivity : BaseActivity<LogsActivityViewModel>() {

    private lateinit var mAdapter: LogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LogsActivityViewModel::class.java)

        init()

        fetchLogs()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report3))

        mAdapter = LogsAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.setOnRefreshListener { fetchLogs() }
    }

    private fun fetchLogs() {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getLogs().observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }
}
