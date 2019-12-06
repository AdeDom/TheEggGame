package com.adedom.theegggame.ui.multi.roominfo

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.networks.MultiApi
import com.adedom.theegggame.data.repositories.MultiRepository
import com.adedom.theegggame.ui.main.MainActivity
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.MyGrid
import com.adedom.utility.ready
import kotlinx.android.synthetic.main.activity_room_info.*
import kotlinx.android.synthetic.main.item_rv_room.*

class RoomInfoActivity : GameActivity() { // 21/7/62

    private lateinit var mViewModel: RoomInfoActivityViewModel
    private lateinit var mAdapter: RoomInfoAdapter

//    private val mRoomInfoItem = arrayListOf<RoomInfoItem>()
//    private var mIsPause = false
//    private var mIsStartGame = false

    companion object {
        lateinit var sRoom: Room
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_info)

        val factory = RoomInfoActivityFactory(MultiRepository(MultiApi()))
        mViewModel = ViewModelProviders.of(this, factory).get(RoomInfoActivityViewModel::class.java)

        sRoom = intent.getParcelableExtra("room") as Room

        init()

        fetchRoomInfo()

        gameLoop = {
            fetchRoomInfo()
//            startGame()
        }

//        mIsPause = true
//        mIsStartGame = true

    }

    private fun init() {
        toolbar.title = getString(R.string.multi_player)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAdapter = RoomInfoAdapter()

        mRecyclerView.also {
            it.layoutManager = GridLayoutManager(baseContext, 2)
            it.addItemDecoration(MyGrid(2, MyGrid.dpToPx(10, resources), true)) // init
            it.adapter = mAdapter
        }

        mTvRoomNo.text = sRoom.room_no
        mTvName.text = sRoom.name
        mTvPeople.text = sRoom.people
        if (sRoom.status == "H") mBtnGo.text = getString(R.string.go)

        mBtnGo.setOnClickListener { getReadyToStartGame() }
        mImgTeamA.setOnClickListener { setTeam("A") }
        mImgTeamB.setOnClickListener { setTeam("B") }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getReadyToStartGame() {
        if (sRoom.status == "H") {
//            var count = 0
//            var teamA = 0
//            var teamB = 0
//            for (item in mRoomInfoItem) {
//                if (item.status_id == "0") {
//                    count += 1
//                }
//
//                if (item.team == "A") {
//                    teamA += 1
//                } else {
//                    teamB += 1
//                }
//            }
//
//            //check team
//            if (teamA == 0 || teamB == 0) {
//                baseContext.toast(R.string.least_one_person_per_team, Toast.LENGTH_LONG)
//                return
//            }
//
//            //check all ready
//            if (count <= 1 && mRoomInfoItem.size != 1) {
//                setReady(ready())
//            } else {
//                baseContext.toast(R.string.player_not_ready, Toast.LENGTH_LONG)
//            }
        } else {
            setReady(ready())
        }
    }

    private fun setReady(ready: String) {
        val roomNo = sRoom.room_no
        val playerId = MainActivity.sPlayerItem.playerId
        mViewModel.setReady(roomNo!!, playerId!!, ready).observe(this, Observer {
            if (it.result == "completed") fetchRoomInfo()
        })
    }

    private fun setTeam(team: String) {
        val roomNo = sRoom.room_no
        val playerId = MainActivity.sPlayerItem.playerId
        mViewModel.setTeam(roomNo!!, playerId!!, team).observe(this, Observer {
            if (it.result == "completed") fetchRoomInfo()
        })
    }

    override fun onBackPressed() {
        val playerId = MainActivity.sPlayerItem.playerId
        mViewModel.deletePlayer(sRoom.room_no!!, playerId!!).observe(this, Observer {
            if (it.result == "completed") finish()
        })
    }

    private fun fetchRoomInfo() {
        mViewModel.getRoomInfo(sRoom.room_no!!).observe(this, Observer {
            mAdapter.setList(it)
        })
    }

    override fun onPause() {
        super.onPause()
        setReady("unready")
    }

//    private fun deletePlayer(playerId: String) {
//        val sql = "SELECT COUNT(*) FROM tbl_room_info WHERE player_id = '${playerId.trim()}'"
//        MyConnect.executeQuery(sql, object : MyResultSet {
//            override fun onResponse(rs: ResultSet) {
//                if (rs.next()) {
//                    if (rs.getInt(1) > 1) {
//                        val sql = "DELETE FROM tbl_room_info " +
//                                "WHERE player_id = '${playerId.trim()}' LIMIT 1"
//                        MyConnect.executeQuery(sql)
//                    }
//                }
//            }
//        })
//    }
//
//    private fun startGame() {
//        var count = 0
//        for ((_, _, _, _, _, _, _, _, status_id) in mRoomInfoItem) {
//            if (status_id == "0") {
//                count += 1
//            }
//        }
//
//        // TODO: 23/05/2562 and room info > 0
//        if (count == 0) {
//            mIsPause = false
//            if (mIsStartGame) {
//                mIsStartGame = false
//
//                val sql = "UPDATE tbl_room \n" +
//                        "SET status_id = '0'\n" +
//                        "WHERE no = '${mNoRoom.trim()}'"
//                MyConnect.executeQuery(sql)
//
//                mHandlerRefresh.removeCallbacks(mRunnableRefresh)
//                baseContext.toast(R.string.start_game)
//            }
//        }
//    }
//
}
