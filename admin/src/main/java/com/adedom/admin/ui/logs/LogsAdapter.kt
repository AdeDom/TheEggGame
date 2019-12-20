package com.adedom.admin.ui.logs

import com.adedom.admin.R
import com.adedom.admin.data.models.Logs
import com.adedom.utility.EMPTY
import com.adedom.utility.extension.loadProfile
import com.adedom.utility.util.BaseAdapter
import kotlinx.android.synthetic.main.item_log.view.*

class LogsAdapter : BaseAdapter<Logs>({ R.layout.item_log }, { holder, position, items ->
    val (name, image, dateIn, timeIn, dateOut, timeOut) = items[position]

    if (image != EMPTY) holder.itemView.ivImage.loadProfile(image)
    holder.itemView.tvName.text = name
    holder.itemView.tvDateIn.text = dateIn
    holder.itemView.tvTimeIn.text = timeIn
    holder.itemView.tvDateOut.text = dateOut
    holder.itemView.tvTimeOut.text = timeOut
})
