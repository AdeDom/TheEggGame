package com.adedom.theegggame.ui.multi.room

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.data.KEY_DATE
import com.adedom.library.data.KEY_TIME
import com.adedom.library.extension.getPrefFile
import com.adedom.library.extension.recyclerGrid
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import com.adedom.library.util.getDateTime
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.*
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : GameActivity<RoomActivityViewModel>() {

    private lateinit var mAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        viewModel = ViewModelProviders.of(this).get(RoomActivityViewModel::class.java)

        init()
    }

    override fun gameLoop() = fetchRoom()

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player), true)

        mAdapter = RoomAdapter()

        mRecyclerView.recyclerGrid { it.adapter = mAdapter }

        mFloatingActionButton.setOnClickListener {
            CreateRoomDialog().show(supportFragmentManager, null)
        }

        mAdapter.onItemClick = { room -> joinRoom(room) }
    }

    private fun joinRoom(room: Room) {
        val playerId = this.getPrefFile(KEY_PLAYER_ID)
        val date = getDateTime(KEY_DATE)
        val time = getDateTime(KEY_TIME)
        viewModel.joinRoom(room.room_no!!, playerId, date, time).observe(this, Observer {
            if (it.result == KEY_COMPLETED) {
                startActivity(
                    Intent(baseContext, RoomInfoActivity::class.java)
                        .putExtra(ROOM, Room(null, room.room_no, room.name, room.people, TAIL))
                )
            } else {
                baseContext.toast(R.string.full, Toast.LENGTH_LONG)
            }
        })
    }

    private fun fetchRoom() = viewModel.getRooms().observe(this, Observer { mAdapter.setList(it) })

}


