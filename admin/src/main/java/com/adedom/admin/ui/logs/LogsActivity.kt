package com.adedom.admin.ui.logs

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.admin.util.DATE_BEGIN
import com.adedom.admin.util.KEY_DAY_MAX
import com.adedom.admin.util.TIME_BEGIN
import com.adedom.library.extension.recyclerVertical
import com.adedom.library.extension.setSwipeRefresh
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import com.adedom.library.util.KEY_DATE
import com.adedom.library.util.getDateTime
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

        mSwipeRefreshLayout.setSwipeRefresh { fetchLogs() }

        mFloatingActionButton.setOnClickListener {
            LogsDialog().show(supportFragmentManager, null)
        }
    }

    private fun fetchLogs(
        dateBegin: String = DATE_BEGIN,
        timeBegin: String = TIME_BEGIN,
        dateEnd: String = getDateTime(KEY_DATE),
        timeEnd: String = KEY_DAY_MAX
    ) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getLogs(dateBegin, timeBegin, dateEnd, timeEnd).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }

    override fun onAttach() {
        fetchLogs(
            LogsActivityViewModel.dateBegin,
            LogsActivityViewModel.timeBegin,
            LogsActivityViewModel.dateEnd,
            LogsActivityViewModel.timeEnd
        )
    }

}
