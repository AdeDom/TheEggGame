package com.adedom.admin.ui.log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adedom.admin.R
import com.adedom.admin.data.models.Log
import com.adedom.utility.EMPTY
import com.adedom.utility.loadProfile
import kotlinx.android.synthetic.main.item_log.view.*

class LogAdapter : RecyclerView.Adapter<LogAdapter.LogHolder>() {

    private var logs = ArrayList<Log>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogHolder =
        LogHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false))

    override fun getItemCount(): Int = logs.size

    override fun onBindViewHolder(holder: LogHolder, position: Int) {
        val (name, image, dateIn, timeIn, dateOut, timeOut) = logs[position]

        if (image != EMPTY) holder.itemView.ivImage.loadProfile(image)
        holder.itemView.tvName.text = name
        holder.itemView.tvDateIn.text = dateIn
        holder.itemView.tvTimeIn.text = timeIn
        holder.itemView.tvDateOut.text = dateOut
        holder.itemView.tvTimeOut.text = timeOut
    }

    fun setList(logs: List<Log>) {
        this.logs = logs as ArrayList<Log>
        notifyDataSetChanged()
    }

    inner class LogHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
