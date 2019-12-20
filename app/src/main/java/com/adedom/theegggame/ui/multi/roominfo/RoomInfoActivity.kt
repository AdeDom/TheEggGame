package com.adedom.theegggame.ui.multi.roominfo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.multi.multi.MultiActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.utility.*
import com.adedom.utility.extension.*
import kotlinx.android.synthetic.main.activity_room_info.*
import kotlinx.android.synthetic.main.item_room.*

class RoomInfoActivity : GameActivity() {

    private lateinit var mViewModel: RoomInfoActivityViewModel
    private lateinit var mAdapter: RoomInfoAdapter
    private var mRoomInfo = arrayListOf<RoomInfo>()

    companion object {
        lateinit var sRoom: Room
        lateinit var sTeam: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_info)

        val factory = RoomInfoActivityFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(RoomInfoActivityViewModel::class.java)

        sRoom = intent.getParcelableExtra(ROOM) as Room

        init()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player))

        mAdapter = RoomInfoAdapter()

        mRecyclerView.recyclerGrid { it.adapter = mAdapter }

        mTvRoomNo.text = sRoom.room_no
        mTvName.text = sRoom.name
        mTvPeople.text = sRoom.people
        if (sRoom.status == HEAD) {
            sTeam = TEAM_A
            mBtnGo.text = getString(R.string.go)
        } else sTeam = TEAM_B

        mBtnGo.setOnClickListener { getReadyToStartGame() }
        mImgTeamA.setOnClickListener {
            sTeam = TEAM_A
            setTeam()
        }
        mImgTeamB.setOnClickListener {
            sTeam = TEAM_B
            setTeam()
        }
    }

    override fun gameLoop() {
        fetchRoomInfo()
        startGame()
    }

    private fun getReadyToStartGame() {
        if (sRoom.status == HEAD) {
            val count = mRoomInfo.count { it.status == READY }
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
                count == mRoomInfo.lastIndex -> setReady(ready())
            }
        } else setReady(ready())
    }

    private fun setReady(ready: String) {
        val roomNo = sRoom.room_no
        mViewModel.setReady(roomNo!!, playerId!!, ready).observe(this, Observer {
            if (it.result == COMPLETED) fetchRoomInfo()
        })
    }

    private fun setTeam() {
        val roomNo = sRoom.room_no
        mViewModel.setTeam(roomNo!!, playerId!!, sTeam).observe(this, Observer {
            if (it.result == COMPLETED) fetchRoomInfo()
        })
    }

    override fun onBackPressed() {
        mViewModel.deletePlayer(sRoom.room_no!!, playerId!!).observe(this, Observer {
            if (it.result == COMPLETED) finish()
        })
        super.onBackPressed()
    }

    private fun fetchRoomInfo() {
        mViewModel.getRoomInfo(sRoom.room_no!!).observe(this, Observer {
            mRoomInfo = it as ArrayList<RoomInfo>
            mAdapter.setList(it)
        })
    }

    override fun onPause() {
        super.onPause()
        setReady(UNREADY)
    }

    private fun startGame() {
        if (mRoomInfo.size == mRoomInfo.count { it.status == READY } && mRoomInfo.size != 0) {
            mViewModel.setRoomOff(sRoom.room_no!!).observe(this, Observer {
                if (it.result == COMPLETED) {
                    finish()
                    startActivity(Intent(baseContext, MultiActivity::class.java))
                    baseContext.toast(R.string.start_game)
                }
            })
        }
    }

}
