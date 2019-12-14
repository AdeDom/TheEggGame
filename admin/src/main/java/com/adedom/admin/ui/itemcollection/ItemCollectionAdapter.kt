package com.adedom.admin.ui.itemcollection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.admin.R
import com.adedom.admin.data.models.ItemCollection
import com.adedom.utility.EMPTY
import com.adedom.utility.getLocality
import com.adedom.utility.loadProfile
import kotlinx.android.synthetic.main.item_single_collection.view.*

class ItemCollectionAdapter : RecyclerView.Adapter<ItemCollectionAdapter.ItemCollectionHolder>() {

    private var items = ArrayList<ItemCollection>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCollectionHolder =
        ItemCollectionHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_single_collection, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemCollectionHolder, position: Int) {
        val (name, image, itemId, qty, latitude, longitude, date, time) = items[position]

        if (image != EMPTY) holder.itemView.ivImage.loadProfile(image)
        when (itemId) {
            1 -> holder.itemView.ivItem.setImageResource(com.adedom.utility.R.drawable.ic_egg)
            2 -> holder.itemView.ivItem.setImageResource(com.adedom.utility.R.drawable.ic_egg_i)
            3 -> holder.itemView.ivItem.setImageResource(com.adedom.utility.R.drawable.ic_egg_ii)
            4 -> holder.itemView.ivItem.setImageResource(com.adedom.utility.R.drawable.ic_egg_iii)
        }

        holder.itemView.tvName.text = name
        holder.itemView.tvQty.text = qty.toString()

        val place = holder.itemView.tvPlace.context.getLocality(latitude, longitude)
        holder.itemView.tvPlace.text = place

        val dateTime = "$date $time"
        holder.itemView.tvDateTime.text = dateTime
    }

    fun setList(items: List<ItemCollection>) {
        this.items = items as ArrayList<ItemCollection>
        notifyDataSetChanged()
    }

    class ItemCollectionHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
