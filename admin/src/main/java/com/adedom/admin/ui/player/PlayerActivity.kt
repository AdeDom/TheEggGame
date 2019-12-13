package com.adedom.admin.ui.player

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.data.models.Player
import com.adedom.admin.data.networks.BaseApi
import com.adedom.admin.data.repositories.BaseRepository
import com.adedom.utility.createSwipeMenu
import com.adedom.utility.textChanged
import com.adedom.utility.util.BaseActivity
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity() {

    private lateinit var mViewModel: PlayerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        factory.viewModel = { PlayerActivityViewModel(BaseRepository(BaseApi.invoke())) }
        mViewModel = ViewModelProviders.of(this, factory).get(PlayerActivityViewModel::class.java)

        init()

        fetchPlayers()

    }

    private fun init() {
        //SwipeMenuListView
        mSwipeMenuListView.also {
            it.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT)

            it.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
                mSwipeMenuListView.smoothOpenMenu(i)
            }
            it.setOnMenuItemClickListener { position, menu, index ->
                var t = ""
                when (index) {
                    0 -> t = "Edit" //todo edit
                    1 -> t = "Delete" //todo delete
                }
                Toast.makeText(baseContext, t, Toast.LENGTH_SHORT).show()
                true
            }
        }
        createSwipeMenu(baseContext, mSwipeMenuListView)

        mSwipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_green_light,
            android.R.color.holo_blue_light
        )

        mSwipeRefreshLayout.setOnRefreshListener {
            fetchPlayers()
        }

        mFloatingActionButton.setOnClickListener {
            //todo insert
        }

        mEtSearch.textChanged {
            Log.d(TAG, ">>$it")
        }

    }

    private fun fetchPlayers() {
        mSwipeRefreshLayout.isRefreshing = true
        mViewModel.getPlayers().observe(this, Observer {
            mSwipeRefreshLayout.isRefreshing = false
            mSwipeMenuListView.adapter = PlayerAdapter(baseContext, it as ArrayList<Player>)
        })
    }

}
