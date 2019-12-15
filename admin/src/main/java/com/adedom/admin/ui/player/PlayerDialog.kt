package com.adedom.admin.ui.player

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.adedom.admin.R
import com.adedom.utility.*

class PlayerDialog : DialogFragment() {

    val TAG = "MyTag"
    private lateinit var mListener: OnAttachListener
    private lateinit var mEtSearch: EditText
    private lateinit var mIvDateBegin: ImageView
    private lateinit var mIvTimeBegin: ImageView
    private lateinit var mTvDateBegin: TextView
    private lateinit var mTvTimeBegin: TextView
    private lateinit var mIvDateEnd: ImageView
    private lateinit var mIvTimeEnd: ImageView
    private lateinit var mTvDateEnd: TextView
    private lateinit var mTvTimeEnd: TextView
    private lateinit var mBtSearch: Button
    private var mDateBegin: String = ""
    private var mTimeBegin: String = ""
    private var mDateEnd: String = ""
    private var mTimeEnd: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_player, null)

        init(view)

        return AlertDialog.Builder(activity!!).dialog(view, R.drawable.ic_player, R.string.player)
    }

    private fun init(view: View) {
        mEtSearch = view.findViewById(R.id.mEtSearch) as EditText
        mIvDateBegin = view.findViewById(R.id.mIvDateBegin) as ImageView
        mIvTimeBegin = view.findViewById(R.id.mIvTimeBegin) as ImageView
        mTvDateBegin = view.findViewById(R.id.mTvDateBegin) as TextView
        mTvTimeBegin = view.findViewById(R.id.mTvTimeBegin) as TextView
        mIvDateEnd = view.findViewById(R.id.mIvDateEnd) as ImageView
        mIvTimeEnd = view.findViewById(R.id.mIvTimeEnd) as ImageView
        mTvDateEnd = view.findViewById(R.id.mTvDateEnd) as TextView
        mTvTimeEnd = view.findViewById(R.id.mTvTimeEnd) as TextView
        mBtSearch = view.findViewById(R.id.mBtSearch) as Button

        val d = "00/00/0000"
        val t = "00:00"
        mTvDateBegin.text = d
        mTvTimeBegin.text = t
        mTvDateEnd.text = d
        mTvTimeEnd.text = t

        mIvDateBegin.setOnClickListener {
            context!!.datePickerDialog { year, month, dayOfMonth ->
                mDateBegin = "$year-$month-$dayOfMonth"
                val date = "$dayOfMonth/$month/$year"
                mTvDateBegin.text = date
            }
        }

        mIvTimeBegin.setOnClickListener {
            context!!.timePickerDialog { hourOfDay, minute ->
                mTimeBegin = "$hourOfDay:$minute"
                mTvTimeBegin.text = mTimeBegin
            }
        }

        mIvDateEnd.setOnClickListener {
            context!!.datePickerDialog { year, month, dayOfMonth ->
                mDateEnd = "$year-$month-$dayOfMonth"
                //todo check date
                val date = "$dayOfMonth/$month/$year"
                mTvDateEnd.text = date
            }
        }

        mIvTimeEnd.setOnClickListener {
            context!!.timePickerDialog { hourOfDay, minute ->
                mTimeEnd = "$hourOfDay:$minute"
                //todo check time
                mTvTimeEnd.text = mTimeEnd
            }
        }

        mBtSearch.setOnClickListener {
            dialog!!.dismiss()
            val search = mEtSearch.getContent()
            mListener.onAttach(search)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as OnAttachListener
    }

}
