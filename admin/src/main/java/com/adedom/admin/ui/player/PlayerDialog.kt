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
import com.adedom.utility.extension.dialog
import com.adedom.utility.extension.getContent
import com.adedom.utility.extension.spinnerLevel
import com.adedom.utility.isCheckOffline
import com.adedom.utility.isCheckOnline
import com.adedom.utility.name
import com.adedom.utility.spinnerLevel

class PlayerDialog : BaseDialogFragment({ R.layout.dialog_player }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(bView)
        return AlertDialog.Builder(activity!!).dialog(bView, R.drawable.ic_player, R.string.player)
    }

    private fun init(view: View) {
        val etName = view.findViewById(R.id.mEtName) as EditText
        val spinner = view.findViewById(R.id.mSpinner) as AppCompatSpinner
        val checkOnline = view.findViewById(R.id.mCheckOnline) as CheckBox
        val checkOffline = view.findViewById(R.id.mCheckOffline) as CheckBox
        val btSearch = view.findViewById(R.id.mBtSearch) as Button

        etName.setText(name)

        spinner.also {
            it.adapter = context!!.spinnerLevel()
            it.setSelection(spinnerLevel)
        }

        checkOnline.isChecked = isCheckOnline

        checkOffline.isChecked = isCheckOffline

        btSearch.setOnClickListener {
            dialog!!.dismiss()
            name = etName.getContent()
            spinnerLevel = spinner.selectedItemPosition
            isCheckOnline = checkOnline.isChecked
            isCheckOffline = checkOffline.isChecked
            listener.onAttach()
        }
    }
}
