package com.adedom.theegggame.ui.dialogs.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Player
import com.adedom.utility.loadProfile
import kotlinx.android.synthetic.main.item_player.view.*

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankHolder>() {

    private var players = arrayListOf<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RankHolder =
        RankHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_player, parent, false)
        )

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: RankHolder, position: Int) {
        //todo animation

        val player = players[position]

        if (players[position].image!!.isNotEmpty()) {
            holder.itemView.mImgProfile.loadProfile(player.image!!)
        }
        holder.itemView.mTvName.text = player.name
        holder.itemView.mTvLevel.text = player.level.toString()
    }

    fun setList(players: List<Player>) {
        this.players = players as ArrayList<Player>
        notifyDataSetChanged()
    }

    inner class RankHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}