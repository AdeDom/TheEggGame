package com.adedom.theegggame.ui.multi.room

import com.adedom.library.util.BaseRecyclerViewAdapter
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapter : BaseRecyclerViewAdapter<Room>({ R.layout.item_room }, { holder, position, items ->
    val room = items[position]
    holder.itemView.mTvRoomNo.text = room.room_no
    holder.itemView.mTvName.text = room.name
    holder.itemView.mTvPeople.text = room.people
})
