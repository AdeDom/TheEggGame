package com.adedom.theegggame.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.adedom.theegggame.R
import com.adedom.theegggame.model.PlayerBean
import com.adedom.theegggame.utility.MyConnect
import com.adedom.theegggame.utility.MyGlide
import com.adedom.theegggame.utility.MyResultSet
import kotlinx.android.synthetic.main.item_player.view.*
import java.sql.ResultSet

class RankDialog : DialogFragment() { // 15/7/62

    val TAG = "RankDialog"
    private val mPlayerItem = arrayListOf<PlayerBean>()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEdtSearch: EditText
    private lateinit var mBtnRank10: Button
    private lateinit var mBtnRank50: Button
    private lateinit var mBtnRank100: Button
    private var mLimit = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_rank, null)

        val builder = AlertDialog.Builder(activity!!)
            .setView(view)

        bindWidgets(view)
        setWidgets()
        setEvents()
        feedPlayer()

        return builder.create()
    }

    private fun bindWidgets(view: View) {
        mEdtSearch = view.findViewById(R.id.mEdtSearch) as EditText
        mBtnRank10 = view.findViewById(R.id.mBtnRank10) as Button
        mBtnRank50 = view.findViewById(R.id.mBtnRank50) as Button
        mBtnRank100 = view.findViewById(R.id.mBtnRank100) as Button
        mRecyclerView = view.findViewById(R.id.mRecyclerView) as RecyclerView
    }

    private fun setWidgets() {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setEvents() {
        mEdtSearch.setOnClickListener {
            feedPlayer()
            Log.d(TAG, "mEdtSearch")
        }
        mBtnRank10.setOnClickListener {
            Log.d(TAG, "10")
            mLimit = "10"
            feedPlayer()
        }
        mBtnRank50.setOnClickListener {
            Log.d(TAG, "50")
            mLimit = "50"
            feedPlayer()
        }
        mBtnRank100.setOnClickListener {
            Log.d(TAG, "100")
            mLimit = "100"
            feedPlayer()
        }
    }

    private fun feedPlayer() {
        var sql = " SELECT * FROM tbl_player "
        if (mEdtSearch.text.toString().trim() != "") {
            sql += " WHERE name LIKE '%${mEdtSearch.text.toString().trim()}%' OR " +
                    "level LIKE '%${mEdtSearch.text.toString().trim()}%' "
        }
        sql += " ORDER BY level DESC , id ASC "
        if (mLimit != "") {
            sql += " LIMIT $mLimit "
        }

        MyConnect.executeQuery(sql, object : MyResultSet {
            override fun onResponse(rs: ResultSet) {
                mPlayerItem.clear()
                while (rs.next()) {
                    val item = PlayerBean(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6)
                    )
                    mPlayerItem.add(item)
                }
                mRecyclerView.adapter = CustomAdapter()
            }
        })
    }

    inner class CustomAdapter : RecyclerView.Adapter<CustomHolder>() {
        override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CustomHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_player, viewGroup, false)
            return CustomHolder(view)
        }

        override fun getItemCount(): Int {
            return mPlayerItem.size
        }

        override fun onBindViewHolder(holder: CustomHolder, position: Int) {
            if (mPlayerItem[position].image.isNotEmpty()) {
                MyGlide.getGlide(context!!, holder.mImgProfile, mPlayerItem[position].image)
            }
            holder.mTvName.text = mPlayerItem[position].name
            holder.mTvLevel.text = mPlayerItem[position].level.toString()

            //todo animation
        }
    }

    inner class CustomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImgProfile: ImageView = itemView.mImgProfile
        val mTvName: TextView = itemView.mTvName
        val mTvLevel: TextView = itemView.mTvLevel
    }
}
