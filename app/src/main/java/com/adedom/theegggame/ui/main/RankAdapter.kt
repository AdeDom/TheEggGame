package com.adedom.theegggame.ui.main

import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.theegggame.util.setImageProfile
import com.adedom.utility.util.BaseAdapter
import kotlinx.android.synthetic.main.item_player.view.*

class RankAdapter : BaseAdapter<Player>({ R.layout.item_player }, { holder, position, items ->
    val player = items[position]
    setImageProfile(holder.itemView.mIvProfile, player.image!!, player.gender!!)
    holder.itemView.mTvName.text = player.name
    holder.itemView.mTvLevel.text = player.level.toString()
})