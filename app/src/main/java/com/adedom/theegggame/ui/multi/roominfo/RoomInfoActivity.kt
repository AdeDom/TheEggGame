package com.adedom.theegggame.ui.multi.roominfo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adedom.library.extension.recyclerGrid
import com.adedom.library.extension.setToolbar
import com.adedom.library.extension.toast
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.data.models.Score
import com.adedom.theegggame.ui.multi.multi.EndGameDialog
import com.adedom.theegggame.ui.multi.multi.MultiActivity
import com.adedom.theegggame.util.*
import kotlinx.android.synthetic.main.activity_room_info.*
import kotlinx.android.synthetic.main.item_room.*

class RoomInfoActivity : GameActivity<RoomInfoActivityViewModel>() {

    private lateinit var mAdapter: RoomInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_info)

        viewModel = ViewModelProvider(this).get(RoomInfoActivityViewModel::class.java)

        viewModel.room = intent.getParcelableExtra(ROOM) ?: return as Room

        init()
    }

    private fun init() {
        this.setToolbar(toolbar, getString(R.string.multi_player), true)

        mAdapter = RoomInfoAdapter()

        mRecyclerView.recyclerGrid { it.adapter = mAdapter }

        mTvRoomNo.text = viewModel.room.room_no
        mTvName.text = viewModel.room.name
        mTvPeople.text = viewModel.room.people
        if (viewModel.room.status == HEAD) {
            viewModel.team = TEAM_A
            mBtGo.text = getString(R.string.go)
        } else viewModel.team = TEAM_B

        mBtGo.setOnClickListener { getReadyToStartGame() }
        mIvTeamA.setOnClickListener {
            viewModel.team = TEAM_A
            setTeam()
        }
        mIvTeamB.setOnClickListener {
            viewModel.team = TEAM_B
            setTeam()
        }
    }

    override fun gameLoop() {
        fetchRoomInfo()
        viewModel.checkStartGame { startGame() }
    }

    private fun getReadyToStartGame() {
        viewModel.checkReadyToStartGame(baseContext) {
            setRoomReady(viewModel.getReady())
        }
    }

    private fun setRoomReady(ready: String) {
        viewModel.getReady(playerId!!, ready).observe(this, Observer {
            if (it.result == KEY_COMPLETED) fetchRoomInfo()
        })
    }

    private fun setTeam() {
        viewModel.setTeam(playerId!!).observe(this, Observer {
            if (it.result == KEY_COMPLETED) fetchRoomInfo()
        })
    }

    override fun onBackPressed() {
        viewModel.deletePlayerRoomInfo(playerId!!).observe(this, Observer {
            if (it.result == KEY_COMPLETED) finish()
        })
        super.onBackPressed()
    }

    private fun fetchRoomInfo() {
        viewModel.getRoomInfo().observe(this, Observer {
            viewModel.roomInfo = it as ArrayList<RoomInfo>
            mAdapter.setList(it)
        })
    }

    override fun onPause() {
        super.onPause()
        setRoomReady(KEY_UNREADY)
    }

    private fun startGame() {
        //TODO start game not completed

        viewModel.setRoomOff().observe(this, Observer {
            if (it.result == KEY_COMPLETED) {
                startActivityForResult(
                    Intent(baseContext, MultiActivity::class.java)
                        .putExtra(ROOM, viewModel.room)
                        .putExtra(TEAM, viewModel.team)
                    , KEY_REQUEST_CODE
                )
                baseContext.toast(R.string.start_game)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode != KEY_REQUEST_CODE -> finish()
            resultCode == Activity.RESULT_CANCELED -> finish()
            resultCode == Activity.RESULT_OK -> {
                val score = data!!.getParcelableExtra(SCORE)?:return as Score

                val bundle = Bundle()
                bundle.putString(TEAM, viewModel.team)
                bundle.putParcelable(SCORE, score)

                val dialog = EndGameDialog()
                dialog.arguments = bundle
                dialog.show(supportFragmentManager, null)
                dialog.isCancelable = false
            }
        }
    }

}
