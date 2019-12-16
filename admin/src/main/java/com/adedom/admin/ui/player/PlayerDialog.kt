package com.adedom.admin.ui.player

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import com.adedom.admin.R
import com.adedom.admin.util.BaseDialogFragment
import com.adedom.utility.*

class PlayerDialog : BaseDialogFragment({ R.layout.dialog_player }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(bView)
        return AlertDialog.Builder(activity!!).dialog(bView, R.drawable.ic_player, R.string.player)
    }

    private fun init(view: View) {
        val etSearch = view.findViewById(R.id.mEtSearch) as EditText
        val spinner = view.findViewById(R.id.mSpinner) as AppCompatSpinner
        val checkOnline = view.findViewById(R.id.mCheckOnline) as CheckBox
        val checkOffline = view.findViewById(R.id.mCheckOffline) as CheckBox
        val btSearch = view.findViewById(R.id.mBtSearch) as Button

        etSearch.setText(search)

        spinner.also {
            it.adapter = context!!.spinnerLevel()
            it.setSelection(spinnerLevel)
        }

        checkOnline.isChecked = isCheckOnline

        checkOffline.isChecked = isCheckOffline

        btSearch.setOnClickListener {
            dialog!!.dismiss()
            search = etSearch.getContent()
            spinnerLevel = spinner.selectedItemPosition
            isCheckOnline = checkOnline.isChecked
            isCheckOffline = checkOffline.isChecked
            listener.onAttach()
        }
    }
}
