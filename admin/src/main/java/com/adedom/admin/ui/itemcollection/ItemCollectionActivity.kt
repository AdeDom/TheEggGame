package com.adedom.admin.ui.itemcollection

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.util.BaseActivity
import com.adedom.library.extension.recyclerVertical
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import kotlinx.android.synthetic.main.activity_item_collection.*

class ItemCollectionActivity : BaseActivity<ItemCollectionActivityViewModel>() {

    private lateinit var mAdapter: ItemCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_collection)

        viewModel = ViewModelProviders.of(this).get(ItemCollectionActivityViewModel::class.java)

        init()

        fetchItemCollection()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.report2), true)

        mAdapter = ItemCollectionAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.apply {
            setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
            setOnRefreshListener { fetchItemCollection() }
        }

        mFloatingActionButton.setOnClickListener {
            ItemCollectionDialog().show(supportFragmentManager, null)
        }
    }

    private fun fetchItemCollection(name: String = "", itemId: Int = 0) {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getItemCollection(name, itemId).observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            if (it.isEmpty()) baseContext.toast(R.string.search_data_not_found, Toast.LENGTH_LONG)
            mAdapter.setList(it)
        })
    }

    override fun onAttach() {
        fetchItemCollection(
            ItemCollectionActivityViewModel.name,
            ItemCollectionActivityViewModel.itemId
        )
    }

}
