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
import com.adedom.utility.extension.dialog
import com.adedom.utility.extension.getContent
import com.adedom.utility.extension.spinnerLevel

class PlayerDialog : BaseDialogFragment({ R.layout.dialog_player }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(bView)
        return AlertDialog.Builder(activity!!).dialog(bView, R.drawable.ic_player, R.string.player)
    }

    private fun init(view: View) {
        val etName = view.findViewById(R.id.mEtName) as EditText
        val spinnerStart = view.findViewById(R.id.mSpinnerStart) as AppCompatSpinner
        val spinnerEnd = view.findViewById(R.id.mSpinnerEnd) as AppCompatSpinner
        val checkOnline = view.findViewById(R.id.mCheckOnline) as CheckBox
        val checkOffline = view.findViewById(R.id.mCheckOffline) as CheckBox
        val checkMale = view.findViewById(R.id.mCheckMale) as CheckBox
        val checkFemale = view.findViewById(R.id.mCheckFemale) as CheckBox
        val btSearch = view.findViewById(R.id.mBtSearch) as Button

        etName.setText(name)

        spinnerStart.apply {
            adapter = context!!.spinnerLevel()
            setSelection(spinnerIndexStart)
        }
        spinnerEnd.apply {
            adapter = context!!.spinnerLevel()
            setSelection(spinnerIndexEnd)
        }

        checkMale.isChecked = isCheckMale
        checkFemale.isChecked = isCheckFemale

        checkOnline.isChecked = isCheckOnline
        checkOffline.isChecked = isCheckOffline

        btSearch.setOnClickListener {
            dialog!!.dismiss()
            name = etName.getContent()
            spinnerIndexStart = spinnerStart.selectedItemPosition
            spinnerIndexEnd = spinnerEnd.selectedItemPosition
            isCheckOnline = checkOnline.isChecked
            isCheckOffline = checkOffline.isChecked
            isCheckMale = checkMale.isChecked
            isCheckFemale = checkFemale.isChecked
            listener.onAttach()
        }
    }
}
