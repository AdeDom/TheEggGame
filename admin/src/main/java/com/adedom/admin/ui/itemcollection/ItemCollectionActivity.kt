package com.adedom.admin.ui.itemcollection

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.data.networks.BaseApi
import com.adedom.admin.data.repositories.BaseRepository
import com.adedom.utility.recyclerVertical
import com.adedom.utility.util.BaseActivity
import kotlinx.android.synthetic.main.activity_item_collection.*

class ItemCollectionActivity : BaseActivity<ItemCollectionActivityViewModel>() {

    private lateinit var mAdapter: ItemCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_collection)

        factory.viewModel = { ItemCollectionActivityViewModel(BaseRepository(BaseApi.invoke())) }
        viewModel =
            ViewModelProviders.of(this, factory).get(ItemCollectionActivityViewModel::class.java)

        init()

        fetchItemCollection()

    }

    private fun init() {
        mAdapter = ItemCollectionAdapter()

        mRecyclerView.recyclerVertical { it.adapter = mAdapter }

        mSwipeRefreshLayout.also {
            it.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_light
            )
            it.setOnRefreshListener {
                fetchItemCollection()
            }
        }
    }

    private fun fetchItemCollection() {
        mSwipeRefreshLayout.isRefreshing = true
        viewModel.getItemCollection().observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            mAdapter.setList(it)
        })
    }
}
