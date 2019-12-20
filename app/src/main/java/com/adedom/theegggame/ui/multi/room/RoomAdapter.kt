package com.adedom.theegggame.ui.multi.room

import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import com.adedom.utility.util.BaseAdapter
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapter : BaseAdapter<Room>({ R.layout.item_room }, { holder, position, items ->
    val room = items[position]
    holder.itemView.mTvRoomNo.text = room.room_no
    holder.itemView.mTvName.text = room.name
    holder.itemView.mTvPeople.text = room.people
})
