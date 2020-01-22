package com.adedom.admin.ui.itemcollection

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.adedom.admin.R
import com.adedom.admin.data.models.Egg
import com.adedom.library.util.BaseSpinnerAdapter

class EggAdapter(
    context: Context,
    items: ArrayList<Egg>,
    layout: (() -> Int)? = null,
    bindView: ((View, Egg) -> Unit)? = null,
    resource: Int = 0
) : BaseSpinnerAdapter<Egg>(
    context,
    items,
    { layout.run { R.layout.item_egg } }, { view, item ->
        bindView.apply {
            val ivImage = view.findViewById(R.id.ivImage) as ImageView
            val tvName = view.findViewById(R.id.tvName) as TextView

            ivImage.setImageResource(item.image)
            tvName.text = item.name
        }
    },
    resource
)
