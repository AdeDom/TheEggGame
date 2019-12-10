package com.adedom.admin.ui.player

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.admin.R
import com.adedom.admin.data.models.Player
import com.adedom.admin.data.networks.BaseApi
import com.adedom.admin.data.repositories.BaseRepository
import com.adedom.utility.createSwipeMenu
import com.adedom.utility.textChanged
import com.baoyz.swipemenulistview.SwipeMenuListView
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    val TAG = "MyTag"
    private lateinit var mViewModel: PlayerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val factory = PlayerActivityFactory(BaseRepository(BaseApi()))
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

        mEtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //todo search
            }
        })

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
