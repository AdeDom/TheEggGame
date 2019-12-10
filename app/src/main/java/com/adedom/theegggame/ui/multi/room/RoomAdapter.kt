package com.adedom.theegggame.ui.multi.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Room
import kotlinx.android.synthetic.main.item_rv_room.view.*

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomHolder>() {

    private var rooms = arrayListOf<Room>()
    var onItemClick: ((Room) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_room, parent, false)
        return RoomHolder(view)
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(holder: RoomHolder, position: Int) {
        val room = rooms[position]

        holder.itemView.mTvRoomNo.text = room.room_no
        holder.itemView.mTvName.text = room.name
        holder.itemView.mTvPeople.text = room.people
    }

    fun setList(rooms: List<Room>) {
        this.rooms = rooms as ArrayList<Room>
        notifyDataSetChanged()
    }

    inner class RoomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(rooms[adapterPosition])
            }
        }
    }

}

