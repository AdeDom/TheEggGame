package com.adedom.theegggame.ui.multi.room

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.ui.dialogs.createroom.CreateRoomDialog
import com.adedom.theegggame.ui.multi.roominfo.RoomInfoActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import com.adedom.utility.extension.getPrefLogin
import com.adedom.utility.extension.recyclerGrid
import com.adedom.utility.extension.setToolbar
import com.adedom.utility.extension.toast
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
        this.setToolbar(toolbar, getString(R.string.multi_player))

        mAdapter = RoomAdapter()

        mRecyclerView.recyclerGrid { it.adapter = mAdapter }

        mFloatingActionButton.setOnClickListener {
            CreateRoomDialog().show(supportFragmentManager, null)
        }

        mAdapter.onItemClick = { room -> joinRoom(room) }
    }

    private fun joinRoom(room: Room) {
        val playerId = this.getPrefLogin(PLAYER_ID)
        val date = getDateTime(DATE)
        val time = getDateTime(TIME)
        viewModel.insertRoomInfo(room.room_no!!, playerId, date, time).observe(this, Observer {
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

    private fun fetchRoom() = viewModel.getRooms().observe(this, Observer { mAdapter.setList(it) })

}


