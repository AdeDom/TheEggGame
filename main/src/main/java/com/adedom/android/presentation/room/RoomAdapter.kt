package com.adedom.android.presentation.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.android.R
import com.adedom.teg.models.response.FetchRoomResponse
import kotlinx.android.synthetic.main.item_room.view.*

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    private val list by lazy { mutableListOf<FetchRoomResponse>() }
    var onClick: ((FetchRoomResponse) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val context = holder.itemView.context
        val roomNo = context?.getString(R.string.room_title_room_no, list[position].roomNo)
        val roomName = context?.getString(R.string.room_title_room_name, list[position].name)
        val people = context?.getString(R.string.room_title_room_people, list[position].people)

        holder.itemView.tvRoomNo.text = roomNo
        holder.itemView.tvRoomName.text = roomName
        holder.itemView.tvRoomPeople.text = people

        holder.itemView.setOnClickListener {
            val room = list[position]
            onClick?.invoke(room)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<FetchRoomResponse>) {
        if (!list.isNullOrEmpty()) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
