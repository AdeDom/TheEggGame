package com.adedom.theegggame.multi

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.dialog.InsertRoomDialog
import com.adedom.theegggame.dialog.InsertRoomPeopleDialog
import com.adedom.theegggame.model.RoomItem
import com.adedom.theegggame.utility.*
import com.adedom.utility.MyLibrary
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.item_rv_room.view.*
import java.sql.ResultSet

class RoomActivity : AppCompatActivity() { // 20/7/62

    val TAG = "RoomActivity"
    private val mRoomItem = arrayListOf<RoomItem>()
    private val mHandlerRefresh = Handler()

    companion object {
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        context = baseContext

        setToolbar()
        setWidgets()
        setEvents()
    }

    override fun onResume() {
        super.onResume()
        mRunnableRefresh.run()

        MyMediaPlayer.music!!.stop()
    }

    private fun setToolbar() {
        toolbar.title = "Multi player"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandlerRefresh.removeCallbacks(mRunnableRefresh)
    }

    override fun onPause() {
        super.onPause()
        mHandlerRefresh.removeCallbacks(mRunnableRefresh)
    }

    private val mRunnableRefresh = object : Runnable {
        override fun run() {
            feedRoom()
            mHandlerRefresh.postDelayed(this, 5000)
        }
    }

    private fun setWidgets() {
        mRecyclerView.layoutManager = GridLayoutManager(baseContext, 2)
        mRecyclerView.addItemDecoration(MyGrid(2, MyGrid.dpToPx(10, resources), true))
    }

    private fun setEvents() {
        mFab.setOnClickListener {
            InsertRoomDialog().show(supportFragmentManager, null)
        }
    }

    private fun feedRoom() {
        mRoomItem.clear()
        val sql = "SELECT * FROM tbl_room ORDER BY status_id DESC , id ASC"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                while (rs.next()) {
                    deleteRoom(rs.getString(2))
                    val item = RoomItem(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                    )
                    mRoomItem.add(item)
                }
                mRecyclerView.adapter = CustomAdapter()
            }
        })
    }

    private fun deleteRoom(noRoom: String) {
        val sql = "SELECT COUNT(*) FROM tbl_room_info WHERE room_no = '${noRoom.trim()}'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                if (rs.next()) {
                    if (rs.getString(1) == "0") {
                        val sqlRoom = "DELETE FROM tbl_room WHERE no = '${noRoom.trim()}'"
                        MyConnect.executeQuery(sqlRoom)

                        val sqlMulti = "DELETE FROM tbl_multi WHERE room_no = '${noRoom.trim()}'"
                        MyConnect.executeQuery(sqlMulti)
                    }
                }
            }
        })
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CustomHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_rv_room, viewGroup, false)
            return CustomHolder(view)
        }

        override fun getItemCount(): Int {
            return mRoomItem.size
        }

        override fun onBindViewHolder(holder: CustomHolder, i: Int) {
            if (mRoomItem[i].status_id == "0") {
                holder.mItemRoom.background = ContextCompat.getDrawable(
                    baseContext,
                    R.drawable.shape_bg_gray
                )
            }

            holder.mTvNo.text = mRoomItem[i].no
            holder.mTvName.text = mRoomItem[i].name
            holder.mTvPeople.text = mRoomItem[i].people
        }
    }

    inner class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvNo: TextView = itemView.mTvNo
        val mTvName: TextView = itemView.mTvName
        val mTvPeople: TextView = itemView.mTvPeople
        val mItemRoom: GridLayout = itemView.mItemRoom

        init {
            itemView.setOnClickListener { checkRoomToJoin() }
        }

        private fun checkRoomToJoin() {
            // TODO: 21/05/2562 check status room ids
            if (mRoomItem[adapterPosition].password.isEmpty()) {
                joinNoPassword()
            } else {
                val bundle = Bundle()
                bundle.putParcelable("room", mRoomItem[adapterPosition])

                val dialog = InsertRoomPeopleDialog()
                dialog.arguments = bundle
                dialog.show(supportFragmentManager, null)
            }
        }

        private fun joinNoPassword() {
            val sql =
                "SELECT COUNT(*) FROM tbl_room_info WHERE room_no = '${mRoomItem[adapterPosition].no}'"
            MyConnect.executeQuery(sql, object : MyResultSet {
                override fun onResponse(rs: ResultSet) {
                    if (rs.next()) {
                        if (rs.getInt(1) < mRoomItem[adapterPosition].people.toInt()) {
                            joinNow()
                        } else {
                            MyLibrary.with(baseContext).showLong(R.string.full)
                        }
                    }
                }
            })
        }

        private fun joinNow() {
            val sql = "INSERT INTO tbl_room_info (room_no, player_id, team, status_id) " +
                    "VALUES ('${mRoomItem[adapterPosition].no}', " +
                    "'${MainActivity.mPlayerItem.playerId}', " +
                    "'${MyCode.rndTeam()}', " +
                    "'0')"
            MyConnect.executeQuery(sql)

            MyIntent().getIntent(
                baseContext,
                GetReadyActivity::class.java,
                mRoomItem[adapterPosition].no,
                mRoomItem[adapterPosition].name,
                mRoomItem[adapterPosition].people,
                "2"
            )
        }
    }
}
