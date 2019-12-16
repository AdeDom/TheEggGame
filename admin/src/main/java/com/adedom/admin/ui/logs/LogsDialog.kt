package com.adedom.admin.ui.logs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.adedom.admin.R
import com.adedom.admin.util.BaseDialogFragment
import com.adedom.utility.*

class LogsDialog : BaseDialogFragment({ R.layout.dialog_logs }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(bView)
        return AlertDialog.Builder(activity!!)
            .dialog(bView, R.drawable.ic_date_black, R.string.logs)
    }

    private fun init(view: View) {
        val ivDateBegin = view.findViewById(R.id.mIvDateBegin) as ImageView
        val ivTimeBegin = view.findViewById(R.id.mIvTimeBegin) as ImageView
        val tvDateBegin = view.findViewById(R.id.mTvDateBegin) as TextView
        val tvTimeBegin = view.findViewById(R.id.mTvTimeBegin) as TextView
        val ivDateEnd = view.findViewById(R.id.mIvDateEnd) as ImageView
        val ivTimeEnd = view.findViewById(R.id.mIvTimeEnd) as ImageView
        val tvDateEnd = view.findViewById(R.id.mTvDateEnd) as TextView
        val tvTimeEnd = view.findViewById(R.id.mTvTimeEnd) as TextView
        val btSearch = view.findViewById(R.id.mBtSearch) as Button

        var dBegin = dateBegin
        var tBegin = timeBegin
        var dEnd = dateEnd
        var tEnd = timeEnd

        tvDateBegin.text = dateBegin
        tvTimeBegin.text = timeBegin
        tvDateEnd.text = dateEnd
        tvTimeEnd.text = timeEnd

        ivDateBegin.setOnClickListener {
            context!!.datePickerDialog { year, month, dayOfMonth ->
                dBegin = "$year-$month-$dayOfMonth"
                tvDateBegin.text = dBegin
            }
        }

        ivTimeBegin.setOnClickListener {
            context!!.timePickerDialog { hourOfDay, minute ->
                tBegin = "$hourOfDay:$minute"
                tvTimeBegin.text = tBegin
            }
        }

        ivDateEnd.setOnClickListener {
            context!!.datePickerDialog { year, month, dayOfMonth ->
                dEnd = "$year-$month-$dayOfMonth"
                tvDateEnd.text = dEnd
            }
        }

        ivTimeEnd.setOnClickListener {
            context!!.timePickerDialog { hourOfDay, minute ->
                tEnd = "$hourOfDay:$minute"
                tvTimeEnd.text = tEnd
            }
        }

        btSearch.setOnClickListener {
            dialog!!.dismiss()
            dateBegin = dBegin
            timeBegin = tBegin
            dateEnd = dEnd
            timeEnd = tEnd
            listener.onAttach()
        }
    }
}
