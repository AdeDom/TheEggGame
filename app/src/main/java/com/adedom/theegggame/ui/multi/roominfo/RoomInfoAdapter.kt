package com.adedom.theegggame.ui.multi.roominfo

import android.view.View
import androidx.core.content.ContextCompat
import com.adedom.library.util.BaseAdapter
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.RoomInfo
import com.adedom.theegggame.util.*
import kotlinx.android.synthetic.main.item_room_info.view.*

class RoomInfoAdapter :
    BaseAdapter<RoomInfo>({ R.layout.item_room_info }, { holder, position, items ->
        val (_, _, _, team, status, _, name, image, level, state, gender) = items[position]
        if (state == KEY_OFFLINE) {
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

        if (position == 0) holder.itemView.mIvKing.visibility = View.VISIBLE

        setImageProfile(holder.itemView.mIvProfile, image!!, gender!!)

        holder.itemView.mTvName.text = name

        holder.itemView.mTvLevel.text = getLevel(level)

        if (status == READY) {
            holder.itemView.mIvReady.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.shape_oval_green
            )
        } else {
            holder.itemView.mIvReady.background = ContextCompat.getDrawable(
                holder.itemView.context,
                R.drawable.shape_oval_red
            )
        }
    })
