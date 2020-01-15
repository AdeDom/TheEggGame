package com.adedom.admin.ui.logs

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.*
import com.adedom.library.extension.recyclerVertical
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import kotlinx.android.synthetic.main.activity_logs.*

class LogsActivity : BaseActivity<LogsActivityViewModel>() {

    private lateinit var mAdapter: LogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        viewModel = ViewModelProviders.of(this).get(LogsActivityViewModel::class.java)

        init()

        fetchLogs()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report3), true)

        mAdapter = LogsAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.apply {
            setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
            setOnRefreshListener { fetchLogs() }
        }

        mFloatingActionButton.setOnClickListener {
            LogsDialog().show(supportFragmentManager, null)
        }
    }

    private fun fetchLogs(
        dateBegin: String = DATE_BEGIN,
        timeBegin: String = TIME_BEGIN,
        dateEnd: String = DATE_NOW,
        timeEnd: String = "23:59"
    ) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getLogs(dateBegin, timeBegin, dateEnd, timeEnd).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }

    override fun onAttach() = fetchLogs(dateBegin, timeBegin, dateEnd, timeEnd)

}
