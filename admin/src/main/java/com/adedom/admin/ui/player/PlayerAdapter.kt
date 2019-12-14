package com.adedom.admin.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.admin.R
import com.adedom.admin.data.models.Player
import com.adedom.utility.EMPTY
import com.adedom.utility.loadProfile
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    private var players = ArrayList<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder =
        PlayerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_player, parent, false)
        )

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val player = players[position]
        if (player.image != EMPTY) holder.itemView.ivImage.loadProfile(player.image!!)
        holder.itemView.tvName.text = player.name
        holder.itemView.tvPlayerId.text = player.playerId
    }

    fun setList(players: List<Player>) {
        this.players = players as ArrayList<Player>
        notifyDataSetChanged()
    }

    inner class PlayerHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}