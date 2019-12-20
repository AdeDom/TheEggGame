package com.adedom.utility.extension

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.adedom.utility.R

fun AlertDialog.Builder.dialog(view: View, icon: Int, title: Int) =
    this.setView(view).setIcon(icon).setTitle(title).create()

fun AlertDialog.Builder.exitDialog(negative: () -> Unit) {
    this.setIcon(R.drawable.ic_exit_black)
        .setTitle(R.string.exit)
        .setPositiveButton(R.string.no) { dialog, which -> dialog.dismiss() }
        .setNegativeButton(R.string.yes) { dialog, which ->
            negative.invoke()
        }.show()
}