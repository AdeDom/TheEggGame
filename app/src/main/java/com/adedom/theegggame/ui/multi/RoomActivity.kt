package com.adedom.theegggame.ui.multi

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.adedom.theegggame.R
import com.adedom.theegggame.data.networks.RoomApi
import com.adedom.theegggame.data.repositories.RoomRepository
import com.adedom.theegggame.ui.adapters.RoomAdapter
import com.adedom.theegggame.ui.dialogs.InsertRoomDialog
import com.adedom.theegggame.ui.factories.RoomActivityFactory
import com.adedom.theegggame.ui.viewmodels.RoomActivityViewModel
import com.adedom.theegggame.util.GameActivity
import com.adedom.theegggame.util.MyGrid
import kotlinx.android.synthetic.main.activity_room.*

class RoomActivity : GameActivity() { // 20/7/62

    val TAG = "RoomActivity"
    private lateinit var mViewModel: RoomActivityViewModel
    private lateinit var mAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val factory = RoomActivityFactory(
            RoomRepository(RoomApi())
        )
        mViewModel = ViewModelProviders.of(this,factory).get(RoomActivityViewModel::class.java)

        init()

        freshRoom()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        toolbar.title = "Multi player"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAdapter = RoomAdapter()

        mRecyclerView.also {
            it.layoutManager = GridLayoutManager(baseContext, 2)
            it.addItemDecoration(MyGrid(2, MyGrid.dpToPx(10, resources), true))
            it.adapter = mAdapter
        }

        mFloatingActionButton.setOnClickListener {
            InsertRoomDialog().show(supportFragmentManager, null)
        }

        mAdapter.onItemClick = {
            //            val sql =
//                "SELECT COUNT(*) FROM tbl_room_info WHERE room_no = '${mRoomItem[adapterPosition].no}'"
//            MyConnect.executeQuery(sql, object : MyResultSet {
//                override fun onResponse(rs: ResultSet) {
//                    if (rs.next()) {
//                        if (rs.getInt(1) < mRoomItem[adapterPosition].people.toInt()) {
//                            joinNow()
//                        } else {
//                            baseContext.toast(R.string.full, Toast.LENGTH_LONG)
//                        }
//                    }
//                }
//            })
        }
    }

    private fun freshRoom() {
        mViewModel.getRooms().observe(this, Observer {
            mAdapter.setList(it)
        })
    }

//        private fun joinNow() {
//            val sql = "INSERT INTO tbl_room_info (room_no, player_id, team, status_id) " +
//                    "VALUES ('${mRoomItem[adapterPosition].no}', " +
//                    "'${MainActivity.sPlayerItem.playerId}', " +
//                    "'${MyCode.rndTeam()}', " +
//                    "'0')"
//            MyConnect.executeQuery(sql)
//
//            startActivity(
//                Intent(baseContext, GetReadyActivity::class.java)
//                    .putExtra("values1", mRoomItem[adapterPosition].no)
//                    .putExtra("values2", mRoomItem[adapterPosition].name)
//                    .putExtra("values3", mRoomItem[adapterPosition].people)
//                    .putExtra("values4", "2")
//            )
//        }
//    }
}

