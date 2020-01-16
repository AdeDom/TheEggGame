package com.adedom.admin.ui.player

import com.adedom.admin.R
import com.adedom.admin.data.models.Player
import com.adedom.admin.util.setImageProfile
import com.adedom.library.util.BaseAdapter
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter : BaseAdapter<Player>({ R.layout.item_player }, { holder, position, items ->
    val player = items[position]
    setImageProfile(holder.itemView.ivImage,player.image!!, player.gender!! )
    holder.itemView.tvName.text = player.name
    holder.itemView.tvPlayerId.text = player.playerId
})

