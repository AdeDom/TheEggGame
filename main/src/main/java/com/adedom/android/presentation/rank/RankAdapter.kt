package com.adedom.android.presentation.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.android.R
import com.adedom.android.util.setImageCircle
import com.adedom.teg.models.response.PlayerInfo
import kotlinx.android.synthetic.main.item_player_rank.view.*

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    private val list by lazy { mutableListOf<PlayerInfo>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player_rank, parent, false)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        val (_, _, name, image, level, _, _, _) = list[position]
        holder.itemView.tvName.text = name
        holder.itemView.tvLevel.text = level.toString()
        holder.itemView.ivImageProfile.setImageCircle(image)
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<PlayerInfo>) {
        if (!list.isNullOrEmpty()) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    inner class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
