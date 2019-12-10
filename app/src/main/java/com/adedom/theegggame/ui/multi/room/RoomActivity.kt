package com.adedom.theegggame.ui.multi.room

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.dialogs.createroom.CreateRoomDialog
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : GameActivity() {

    private lateinit var mViewModel: RoomActivityViewModel
    private lateinit var mAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val factory = RoomActivityFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(RoomActivityViewModel::class.java)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun gameLoop() = fetchRoom()

    private fun init() {
        toolbar.title = getString(R.string.multi_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAdapter = RoomAdapter()

        mRecyclerView.also {
            it.layoutManager = GridLayoutManager(baseContext, 2)
            it.addItemDecoration(ItemDecoration(2, ItemDecoration.dpToPx(10, resources), true))
            it.adapter = mAdapter
        }

        mFloatingActionButton.setOnClickListener {
            CreateRoomDialog().show(supportFragmentManager, null)
        }

        mAdapter.onItemClick = { room ->
            val playerId = this.getPrefLogin(PLAYER_ID)
            val date = getDateTime(DATE)
            val time = getDateTime(TIME)
            mViewModel.insertRoomInfo(room.room_no!!, playerId, date, time).observe(this, Observer {
                if (it.result == COMPLETED) {
                    startActivity(
                        Intent(baseContext, RoomInfoActivity::class.java)
                            .putExtra(ROOM, Room(null, room.room_no, room.name, room.people, TAIL))
                    )
                } else {
                    baseContext.toast(R.string.full, Toast.LENGTH_LONG)
                }
            })
        }
    }

    private fun fetchRoom() = mViewModel.getRooms().observe(this, Observer { mAdapter.setList(it) })

}

