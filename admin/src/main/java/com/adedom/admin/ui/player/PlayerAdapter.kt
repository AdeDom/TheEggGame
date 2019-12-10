package com.adedom.admin.ui.player

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.adedom.admin.R
import com.adedom.admin.data.models.Player
import com.adedom.utility.EMPTY
import com.adedom.utility.OFFLINE
import com.adedom.utility.getLevel
import com.adedom.utility.loadProfile
import java.util.*

class PlayerAdapter(
    private val mContext: Context,
    private val players: ArrayList<Player>
) : ArrayAdapter<Player>(mContext, 0, players) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            val inflater = LayoutInflater.from(mContext)
            view = inflater.inflate(R.layout.item_player, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val player = players[position]
        holder.tvName.text = player.name
        holder.tvPlayerId.text = player.playerId
        holder.tvLevel.text = getLevel(player.level)
        if (player.image != EMPTY) holder.ivImage.loadProfile(player.image!!)
        if (player.state == OFFLINE) holder.ivState.visibility = View.INVISIBLE

        return view!!
    }

    inner class ViewHolder(view: View) {
        val tvName = view.findViewById(R.id.mTvName) as TextView
        val tvPlayerId = view.findViewById(R.id.mTvPlayerId) as TextView
        val tvLevel = view.findViewById(R.id.mTvLevel) as TextView
        val ivImage = view.findViewById(R.id.mIvImage) as ImageView
        val ivState = view.findViewById(R.id.mIvState) as ImageView
    }

}
