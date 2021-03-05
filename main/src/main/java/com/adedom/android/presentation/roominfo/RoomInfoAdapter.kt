package com.adedom.android.presentation.roominfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.adedom.android.R
import com.adedom.android.util.setImageCircle
import com.adedom.teg.models.websocket.RoomInfoPlayers
import com.adedom.teg.util.TegConstant
import kotlinx.android.synthetic.main.item_room_info.view.*

class RoomInfoAdapter : RecyclerView.Adapter<RoomInfoAdapter.RoomInfoViewHolder>() {

    private val list by lazy { mutableListOf<RoomInfoPlayers>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_room_info, parent, false)
        return RoomInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomInfoViewHolder, position: Int) {
        val view = holder.itemView
        val context = holder.itemView.context
        val (_, _, name, image, level, _, _, _, role, status, team) = list[position]

        view.tvName.text = name
        view.tvLevel.text = context.getString(R.string.level, level)
        view.ivImageProfile.setImageCircle(image)

        if (role == TegConstant.ROOM_ROLE_HEAD) {
            view.ivRoleHead.visibility = View.VISIBLE
        }

        if (team == TegConstant.TEAM_A) {
            view.layoutRoomInfo.background =
                ContextCompat.getDrawable(context, R.drawable.shape_team_a)
        } else {
            view.layoutRoomInfo.background =
                ContextCompat.getDrawable(context, R.drawable.shape_team_b)
        }

        if (status == TegConstant.ROOM_STATUS_READY) {
            view.ivStatus.background =
                ContextCompat.getDrawable(context, R.drawable.shape_oval_green)
        } else {
            view.ivStatus.background =
                ContextCompat.getDrawable(context, R.drawable.shape_oval_red)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<RoomInfoPlayers>) {
        if (!list.isNullOrEmpty()) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class RoomInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
