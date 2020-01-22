package com.adedom.admin.ui.itemcollection

import com.adedom.admin.R
import com.adedom.admin.data.imageUrl
import com.adedom.admin.data.models.ItemCollection
import com.adedom.admin.util.KEY_LATLNG_ZERO
import com.adedom.admin.util.KEY_OTHER
import com.adedom.library.extension.getLocality
import com.adedom.library.extension.loadCircle
import com.adedom.library.util.BaseAdapter
import com.adedom.library.util.KEY_EMPTY
import kotlinx.android.synthetic.main.item_single_collection.view.*

class ItemCollectionAdapter :
    BaseAdapter<ItemCollection>({ R.layout.item_single_collection }, { holder, position, items ->
        val (name, image, _, _, itemId, qty, latitude, longitude, date, time) = items[position]

        if (image != KEY_EMPTY) holder.itemView.ivImage.loadCircle(imageUrl(image))
        when (itemId) {
            1 -> holder.itemView.ivItem.setImageResource(R.drawable.ic_egg)
            2 -> holder.itemView.ivItem.setImageResource(R.drawable.ic_egg_i)
            3 -> holder.itemView.ivItem.setImageResource(R.drawable.ic_egg_ii)
            4 -> holder.itemView.ivItem.setImageResource(R.drawable.ic_egg_iii)
        }

        holder.itemView.tvName.text = name
        holder.itemView.tvQty.text = qty.toString()

        val place = if (latitude == KEY_LATLNG_ZERO && longitude == KEY_LATLNG_ZERO) KEY_OTHER
        else holder.itemView.tvPlace.context.getLocality(latitude, longitude)
        holder.itemView.tvPlace.text = place

        val dateTime = "$date $time"
        holder.itemView.tvDateTime.text = dateTime
    })
