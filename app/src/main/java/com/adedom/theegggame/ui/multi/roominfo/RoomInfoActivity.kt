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
    private var mRoomInfo = arrayListOf<RoomInfo>()

    companion object {
        lateinit var sRoom: Room
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_info)

        viewModel = ViewModelProviders.of(this).get(RoomInfoActivityViewModel::class.java)

        sRoom = intent.getParcelableExtra(ROOM) as Room

        init()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player), true)

        mAdapter = RoomInfoAdapter()

        mRecyclerView.recyclerGrid { it.adapter = mAdapter }

        mTvRoomNo.text = sRoom.room_no
        mTvName.text = sRoom.name
        mTvPeople.text = sRoom.people
        if (sRoom.status == HEAD) {
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
        if (sRoom.status == HEAD) {
            val count = mRoomInfo.count { it.status == KEY_READY }
            val teamA = mRoomInfo.count { it.team == TEAM_A }
            val teamB = mRoomInfo.count { it.team == TEAM_B }

            when {
                //player not yet
                count != mRoomInfo.lastIndex -> {
                    baseContext.toast(R.string.player_not_ready, Toast.LENGTH_LONG)
                    return
                }
                //check team
                teamA == 0 || teamB == 0 -> {
                    baseContext.toast(R.string.least_one_person_per_team, Toast.LENGTH_LONG)
                    return
                }
                count == mRoomInfo.lastIndex -> setRoomReady(viewModel.getReady())
            }
        } else setRoomReady(viewModel.getReady())
    }

    private fun setRoomReady(ready: String) {
        val roomNo = sRoom.room_no
        viewModel.getReady(roomNo!!, playerId!!, ready).observe(this, Observer {
            if (it.result == KEY_COMPLETED) fetchRoomInfo()
        })
    }

    private fun setTeam() {
        val roomNo = sRoom.room_no
        viewModel.setTeam(roomNo!!, playerId!!, RoomInfoActivityViewModel.team)
            .observe(this, Observer {
                if (it.result == KEY_COMPLETED) fetchRoomInfo()
            })
    }

    override fun onBackPressed() {
        viewModel.deletePlayerRoomInfo(sRoom.room_no!!, playerId!!).observe(this, Observer {
            if (it.result == KEY_COMPLETED) finish()
        })
        super.onBackPressed()
    }

    private fun fetchRoomInfo() {
        viewModel.getRoomInfo(sRoom.room_no!!).observe(this, Observer {
            mRoomInfo = it as ArrayList<RoomInfo>
            mAdapter.setList(it)
        })
    }

    override fun onPause() {
        super.onPause()
        setRoomReady(KEY_UNREADY)
    }

    private fun startGame() {
        if (mRoomInfo.size == mRoomInfo.count { it.status == KEY_READY } && mRoomInfo.size != 0) {
            viewModel.setRoomOff(sRoom.room_no!!).observe(this, Observer {
                if (it.result == KEY_COMPLETED) {
                    finish()
                    startActivity(Intent(baseContext, MultiActivity::class.java))
                    baseContext.toast(R.string.start_game)
                }
            })
        }
    }

}
