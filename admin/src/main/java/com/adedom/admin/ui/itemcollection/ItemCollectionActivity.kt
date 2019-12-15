package com.adedom.admin.ui.itemcollection

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

class ItemCollectionActivity : BaseActivity<ItemCollectionActivityViewModel>() {

    private lateinit var mAdapter: ItemCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ItemCollectionActivityViewModel::class.java)

        init()

        fetchItemCollection()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report2))

        mAdapter = ItemCollectionAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.setOnRefreshListener { fetchItemCollection() }
    }

    private fun fetchItemCollection() {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getItemCollection().observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }
}
