package com.adedom.theegggame.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.MainActivity
import com.adedom.theegggame.R
import com.adedom.theegggame.model.ChatItem
import com.adedom.theegggame.single.SingleActivity
import com.adedom.theegggame.utility.MyConnect
import com.adedom.theegggame.utility.MyIon
import com.adedom.theegggame.utility.MyResultSet
import com.sevenheaven.segmentcontrol.SegmentControl
import kotlinx.android.synthetic.main.item_chat.view.*
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.util.*

class ChatSingleDialog : DialogFragment() { // 19/7/19

    val TAG = "ChatSingleDialog"
    private val mChatSingleItem = arrayListOf<ChatItem>()
    private lateinit var mSegment: SegmentControl
    private lateinit var mEdtChat: EditText
    private lateinit var mRecyclerView: RecyclerView
    private val mHandlerRefreshChat = Handler()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_chat, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)
            .setIcon(R.drawable.ic_chat)
            .setTitle("Chat")

        bindWidgets(view)
        setWidgets()
        setEvents()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mSegment = view.findViewById(R.id.mSegment) as SegmentControl
        mEdtChat = view.findViewById(R.id.mEdtChat) as EditText
        mRecyclerView = view.findViewById(R.id.mRecyclerView) as RecyclerView
        mRecyclerView.layoutManager =
            LinearLayoutManager(context) // init
    }

    private fun setWidgets() {
        val titleChat = arrayOf("Public", "Private")
        mSegment.setText(*titleChat)

        mSegment.setSelectedIndex(SingleActivity.mSegmentIndex)
        mEdtChat.isEnabled = SingleActivity.mSegmentIndex == 0
    }

    private fun setEvents() {
        mSegment.setOnSegmentControlClickListener { index ->
            SingleActivity.mSegmentIndex = index
            mEdtChat.isEnabled = SingleActivity.mSegmentIndex == 0
        }

        mEdtChat.setOnClickListener {
            insertChat()
        }
    }

    private fun insertChat() {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time = df.format(Calendar.getInstance().time)

        val sql = "INSERT INTO tbl_chat (player_id, time, message, type) " +
                "VALUES ('${MainActivity.sPlayerItem.playerId}', " +
                "'${time.toString().trim()}', " +
                "'${mEdtChat.text.toString().trim()}', " +
                "'1')"
        MyConnect.executeQuery(sql)

        mEdtChat.setText("")
    }

    private fun feedChat() {
        // todo update chat

        mChatSingleItem.clear()
        val sql = "SELECT c.player_id, c.time, c.message, p.image, c.type\n" +
                "FROM tbl_chat AS c , tbl_player AS p\n" +
                "WHERE c.player_id = p.id AND c.type = '${SingleActivity.mSegmentIndex + 1}'"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                while (rs.next()) {
                    val item = ChatItem(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                    )
                    mChatSingleItem.add(item)
                }

                if (SingleActivity.mSegmentIndex == 0) {
                    mChatSingleItem += SingleActivity.mChatItem
                    mChatSingleItem.sortBy { chatItem -> chatItem.time }
                }

                mRecyclerView.adapter = CustomAdapter()
            }
        })
    }

    private val mRunnableRefreshChat = object : Runnable {
        override fun run() {
            deleteChat()
            feedChat()
            mHandlerRefreshChat.postDelayed(this, 500)
        }
    }

    private fun deleteChat() {
        val sql = "SELECT id FROM tbl_chat WHERE type = '1' ORDER BY id DESC"
        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                var count = 0
                var id = ""
                while (rs.next()) {
                    count++
                    id = rs.getString(1)
                }

                if (count > 8) {
                    val sql = "DELETE FROM tbl_chat WHERE id = '$id'"
                    MyConnect.executeQuery(sql)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mRunnableRefreshChat.run()
    }

    override fun onPause() {
        super.onPause()
        mHandlerRefreshChat.removeCallbacks(mRunnableRefreshChat)
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CustomHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_chat, viewGroup, false)
            return CustomHolder(view)
        }

        override fun getItemCount(): Int {
            return mChatSingleItem.size
        }

        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
            val (playerId, time, message, image, type) = mChatSingleItem[position]

            if (type == "3") {
                holder.mImgProfile.visibility = View.GONE
            } else {
                if (image.isNotEmpty()) {
                    MyIon.getIon(holder.mImgProfile, image)
                }
            }
            holder.mTvMessage.text = message
            holder.mTvTime.text = time.substring(11)
        }
    }

    inner class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImgProfile: ImageView = itemView.mImgProfile
        val mTvMessage: TextView = itemView.mTvMessage
        val mTvTime: TextView = itemView.mTvTime
    }
}
