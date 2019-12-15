package com.adedom.admin.ui.player

import com.adedom.admin.R
import com.adedom.admin.data.models.Player
import com.adedom.utility.EMPTY
import com.adedom.utility.loadProfile
import com.adedom.utility.util.BaseAdapter
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter : BaseAdapter<Player>({ R.layout.item_player }, { holder, position, items ->
    val player = items[position]
    if (player.image != EMPTY) holder.itemView.ivImage.loadProfile(player.image!!)
    holder.itemView.tvName.text = player.name
    holder.itemView.tvPlayerId.text = player.playerId
})
