package com.adedom.theegggame.ui.multi.roominfo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.library.extension.recyclerGrid
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.ui.multi.multi.MultiActivity
import com.adedom.theegggame.util.*
import kotlinx.android.synthetic.main.activity_room_info.*
import kotlinx.android.synthetic.main.item_room.*

class RoomInfoActivity : GameActivity<RoomInfoActivityViewModel>() {

    private lateinit var mAdapter: RoomInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_info)

        viewModel = ViewModelProviders.of(this).get(RoomInfoActivityViewModel::class.java)

        RoomInfoActivityViewModel.sRoom = intent.getParcelableExtra(ROOM) as Room

        init()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player), true)

        mAdapter = RoomInfoAdapter()

        mRecyclerView.recyclerGrid { it.adapter = mAdapter }

        mTvRoomNo.text = RoomInfoActivityViewModel.sRoom.room_no
        mTvName.text = RoomInfoActivityViewModel.sRoom.name
        mTvPeople.text = RoomInfoActivityViewModel.sRoom.people
        if (RoomInfoActivityViewModel.sRoom.status == HEAD) {
            RoomInfoActivityViewModel.team = TEAM_A
            mBtGo.text = getString(R.string.go)
        } else RoomInfoActivityViewModel.team = TEAM_B

        mBtGo.setOnClickListener { getReadyToStartGame() }
        mIvTeamA.setOnClickListener {
            RoomInfoActivityViewModel.team = TEAM_A
            setTeam()
        }
        mIvTeamB.setOnClickListener {
            RoomInfoActivityViewModel.team = TEAM_B
            setTeam()
        }
    }

    override fun gameLoop() {
        fetchRoomInfo()
        startGame()
    }

    private fun getReadyToStartGame() {
        if (RoomInfoActivityViewModel.sRoom.status == HEAD) {
            val count = viewModel.roomInfo.count { it.status == KEY_READY }
            val teamA = viewModel.roomInfo.count { it.team == TEAM_A }
            val teamB = viewModel.roomInfo.count { it.team == TEAM_B }

            when {
                //player not yet
                count != viewModel.roomInfo.lastIndex -> {
                    baseContext.toast(R.string.player_not_ready, Toast.LENGTH_LONG)
                    return
                }
                //check team
                teamA == 0 || teamB == 0 -> {
                    baseContext.toast(R.string.least_one_person_per_team, Toast.LENGTH_LONG)
                    return
                }
                count == viewModel.roomInfo.lastIndex -> setRoomReady(viewModel.getReady())
            }
        } else setRoomReady(viewModel.getReady())
    }

    private fun setRoomReady(ready: String) {
        val roomNo = RoomInfoActivityViewModel.sRoom.room_no
        viewModel.getReady(roomNo!!, playerId!!, ready).observe(this, Observer {
            if (it.result == KEY_COMPLETED) fetchRoomInfo()
        })
    }

    private fun setTeam() {
        val roomNo = RoomInfoActivityViewModel.sRoom.room_no
        viewModel.setTeam(roomNo!!, playerId!!, RoomInfoActivityViewModel.team)
            .observe(this, Observer {
                if (it.result == KEY_COMPLETED) fetchRoomInfo()
            })
    }

    override fun onBackPressed() {
        viewModel.deletePlayerRoomInfo(RoomInfoActivityViewModel.sRoom.room_no!!, playerId!!)
            .observe(this, Observer {
                if (it.result == KEY_COMPLETED) finish()
            })
        super.onBackPressed()
    }

    private fun fetchRoomInfo() {
        viewModel.getRoomInfo(RoomInfoActivityViewModel.sRoom.room_no!!).observe(this, Observer {
            viewModel.roomInfo = it as ArrayList<RoomInfo>
            mAdapter.setList(it)
        })
    }

    override fun onPause() {
        super.onPause()
        setRoomReady(KEY_UNREADY)
    }

    private fun startGame() {
        if (viewModel.roomInfo.size == viewModel.roomInfo.count { it.status == KEY_READY } && viewModel.roomInfo.size != 0) {
            viewModel.setRoomOff(RoomInfoActivityViewModel.sRoom.room_no!!).observe(this, Observer {
                if (it.result == KEY_COMPLETED) {
                    finish()
                    startActivity(Intent(baseContext, MultiActivity::class.java))
                    baseContext.toast(R.string.start_game)
                }
            })
        }
    }

}
