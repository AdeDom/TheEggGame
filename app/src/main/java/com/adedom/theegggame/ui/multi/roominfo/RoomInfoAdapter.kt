package com.adedom.theegggame.ui.multi.roominfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.utility.*
import com.adedom.utility.extension.loadProfile
import kotlinx.android.synthetic.main.item_room_info.view.*

class RoomInfoAdapter : RecyclerView.Adapter<RoomInfoAdapter.RoomInfoHolder>() {

    private var items = arrayListOf<RoomInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomInfoHolder =
        RoomInfoHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_room_info, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RoomInfoHolder, position: Int) {
        val (_, _, _, team, status, _, name, image, level, state) = items[position]
        if (state == OFFLINE) {
            holder.itemView.mBgPlayer.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.shape_bg_gray
            )
        } else {
            if (team == TEAM_A) {
                holder.itemView.mBgPlayer.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.shape_bg_player_red
                )
            } else {
                holder.itemView.mBgPlayer.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.shape_bg_player_blue
                )
            }
        }

        if (position == 0) holder.itemView.mImgKing.visibility = View.VISIBLE

        if (image != EMPTY) holder.itemView.mImgProfile.loadProfile(image!!)

        holder.itemView.mTvName.text = name

        holder.itemView.mTvLevel.text = getLevel(level)

        if (status == READY) {
            holder.itemView.mImgReady.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.shape_oval_green
            )
        } else {
            holder.itemView.mImgReady.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.shape_oval_red
            )
        }
    }

    fun setList(items: List<RoomInfo>) {
        this.items = items as ArrayList<RoomInfo>
        notifyDataSetChanged()
    }

    inner class RoomInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}