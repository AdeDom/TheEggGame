package com.adedom.admin.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment(private val resource: () -> Int) : DialogFragment() {
    lateinit var listener: OnAttachListener
    lateinit var bView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnAttachListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bView = activity!!.layoutInflater.inflate(resource.invoke(), null)
        return super.onCreateDialog(savedInstanceState)
    }
}