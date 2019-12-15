package com.adedom.admin.ui.player

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.DialogFragment
import com.adedom.admin.R
import com.adedom.admin.util.OnAttachListener
import com.adedom.utility.*

class PlayerDialog : DialogFragment() {

    private lateinit var mListener: OnAttachListener
    private lateinit var mEtSearch: EditText
    private lateinit var mSpinner: AppCompatSpinner
    private lateinit var mCheckOnline: CheckBox
    private lateinit var mCheckOffline: CheckBox
    private lateinit var mIvDateBegin: ImageView
    private lateinit var mIvTimeBegin: ImageView
    private lateinit var mTvDateBegin: TextView
    private lateinit var mTvTimeBegin: TextView
    private lateinit var mIvDateEnd: ImageView
    private lateinit var mIvTimeEnd: ImageView
    private lateinit var mTvDateEnd: TextView
    private lateinit var mTvTimeEnd: TextView
    private lateinit var mBtSearch: Button
    private var mDateBegin: String = dateBegin
    private var mTimeBegin: String = timeBegin
    private var mDateEnd: String = dateEnd
    private var mTimeEnd: String = timeEnd

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_player, null)

        init(view)

        return AlertDialog.Builder(activity!!).dialog(view, R.drawable.ic_player, R.string.player)
    }

    private fun init(view: View) {
        mEtSearch = view.findViewById(R.id.mEtSearch) as EditText
        mSpinner = view.findViewById(R.id.mSpinner) as AppCompatSpinner
        mCheckOnline = view.findViewById(R.id.mCheckOnline) as CheckBox
        mCheckOffline = view.findViewById(R.id.mCheckOffline) as CheckBox
        mIvDateBegin = view.findViewById(R.id.mIvDateBegin) as ImageView
        mIvTimeBegin = view.findViewById(R.id.mIvTimeBegin) as ImageView
        mTvDateBegin = view.findViewById(R.id.mTvDateBegin) as TextView
        mTvTimeBegin = view.findViewById(R.id.mTvTimeBegin) as TextView
        mIvDateEnd = view.findViewById(R.id.mIvDateEnd) as ImageView
        mIvTimeEnd = view.findViewById(R.id.mIvTimeEnd) as ImageView
        mTvDateEnd = view.findViewById(R.id.mTvDateEnd) as TextView
        mTvTimeEnd = view.findViewById(R.id.mTvTimeEnd) as TextView
        mBtSearch = view.findViewById(R.id.mBtSearch) as Button

        mEtSearch.setText(search)

        mSpinner.adapter = context!!.spinnerLevel()
        mSpinner.setSelection(spinnerLevel)

        mCheckOnline.isChecked = isCheckOnline
        mCheckOffline.isChecked = isCheckOffline

        mTvDateBegin.text = dateBegin
        mTvTimeBegin.text = timeBegin
        mTvDateEnd.text = dateEnd
        mTvTimeEnd.text = timeEnd

        mIvDateBegin.setOnClickListener {
            context!!.datePickerDialog { year, month, dayOfMonth ->
                mDateBegin = "$year-$month-$dayOfMonth"
                mTvDateBegin.text = mDateBegin
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
                mTvDateEnd.text = mDateEnd
            }
        }

        mIvTimeEnd.setOnClickListener {
            context!!.timePickerDialog { hourOfDay, minute ->
                mTimeEnd = "$hourOfDay:$minute"
                mTvTimeEnd.text = mTimeEnd
            }
        }

        mBtSearch.setOnClickListener {
            dialog!!.dismiss()
            search = mEtSearch.getContent()
            spinnerLevel = mSpinner.selectedItemPosition
            isCheckOnline = mCheckOnline.isChecked
            isCheckOffline = mCheckOffline.isChecked
            dateBegin = mDateBegin
            timeBegin = mTimeBegin
            dateEnd = mDateEnd
            timeEnd = mTimeEnd
            mListener.onAttach()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as OnAttachListener
    }

}
