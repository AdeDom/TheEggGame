package com.adedom.android.presentation.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adedom.android.R
import com.adedom.android.util.setImageCircle
import com.adedom.teg.models.response.PlayerInfo
import kotlinx.android.synthetic.main.item_player_rank.view.*

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<PlayerInfo>() {
        override fun areItemsTheSame(oldItem: PlayerInfo, newItem: PlayerInfo): Boolean {
            return oldItem.playerId == newItem.playerId
        }

        override fun areContentsTheSame(oldItem: PlayerInfo, newItem: PlayerInfo): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    private val list: MutableList<PlayerInfo>
        get() = asyncListDiffer.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player_rank, parent, false)
        return RankViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.itemView.apply {
            val (_, _, name, image, level, _, _, _) = list[position]

            tvName.text = name
            tvLevel.text = level.toString()
            ivImageProfile.setImageCircle(image)
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<PlayerInfo>) = asyncListDiffer.submitList(list)

    inner class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
