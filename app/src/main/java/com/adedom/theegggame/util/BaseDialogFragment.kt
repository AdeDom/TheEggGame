package com.adedom.theegggame.util

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel

abstract class BaseDialogFragment<VM : ViewModel>(private val resource: () -> Int) :
    DialogFragment() {

    val TAG = "BaseDialogFragment"
    lateinit var viewModel: VM
    lateinit var bView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bView = activity!!.layoutInflater.inflate(resource.invoke(), null)
        return super.onCreateDialog(savedInstanceState)
    }
}