package com.adedom.theegggame.util.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String?) =
    Snackbar.make(this, message.toString(), Snackbar.LENGTH_INDEFINITE).apply {
        setAction(android.R.string.ok) {
        }
    }.show()
