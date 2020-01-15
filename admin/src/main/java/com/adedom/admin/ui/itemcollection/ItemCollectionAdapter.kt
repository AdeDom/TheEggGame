package com.adedom.admin.ui.itemcollection

import com.adedom.admin.R
import com.adedom.admin.data.models.ItemCollection
import com.adedom.library.extension.getLocality
import com.adedom.library.extension.loadCircle
import com.adedom.utility.EMPTY
import com.adedom.utility.data.BASE_URL
import com.adedom.utility.util.BaseAdapter
import kotlinx.android.synthetic.main.item_single_collection.view.*

class ItemCollectionAdapter :
    BaseAdapter<ItemCollection>({ R.layout.item_single_collection }, { holder, position, items ->
        val (name, image, _, _, itemId, qty, latitude, longitude, date, time) = items[position]

        if (image != EMPTY) holder.itemView.ivImage.loadCircle("$BASE_URL../profiles/$image")
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
    })