package com.adedom.theegggame.ui.main

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.adedom.library.extension.dialogFragment
import com.adedom.library.util.BaseDialogFragment
import com.adedom.theegggame.R

class AboutDialog : BaseDialogFragment<MainActivityViewModel>({ R.layout.dialog_about }) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        return AlertDialog.Builder(activity!!).dialogFragment(v, R.drawable.ic_h2p, R.string.about)
    }
}
