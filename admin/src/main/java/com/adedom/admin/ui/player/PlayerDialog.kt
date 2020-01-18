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
import com.adedom.admin.util.extension.spinnerLevel
import com.adedom.library.extension.dialogFragment
import com.adedom.library.extension.getContent

class PlayerDialog : BaseDialogFragment({ R.layout.dialog_player }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        init(v)
        return AlertDialog.Builder(activity!!)
            .dialogFragment(v, R.drawable.ic_player, R.string.player)
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

        etName.setText(PlayerActivityViewModel.name)

        spinnerStart.apply {
            adapter = context!!.spinnerLevel()
            setSelection(PlayerActivityViewModel.spinnerIndexStart)
        }
        spinnerEnd.apply {
            adapter = context!!.spinnerLevel()
            setSelection(PlayerActivityViewModel.spinnerIndexEnd)
        }

        checkMale.isChecked = PlayerActivityViewModel.isCheckMale
        checkFemale.isChecked = PlayerActivityViewModel.isCheckFemale

        checkOnline.isChecked = PlayerActivityViewModel.isCheckOnline
        checkOffline.isChecked = PlayerActivityViewModel.isCheckOffline

        btSearch.setOnClickListener {
            dialog!!.dismiss()
            PlayerActivityViewModel.name = etName.getContent()
            PlayerActivityViewModel.spinnerIndexStart = spinnerStart.selectedItemPosition
            PlayerActivityViewModel.spinnerIndexEnd = spinnerEnd.selectedItemPosition
            PlayerActivityViewModel.isCheckOnline = checkOnline.isChecked
            PlayerActivityViewModel.isCheckOffline = checkOffline.isChecked
            PlayerActivityViewModel.isCheckMale = checkMale.isChecked
            PlayerActivityViewModel.isCheckFemale = checkFemale.isChecked
            listener.onAttach()
        }
    }
}
