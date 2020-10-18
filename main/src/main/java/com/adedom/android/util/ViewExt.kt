package com.adedom.android.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(message: String?) =
    Snackbar.make(this, message.toString(), Snackbar.LENGTH_INDEFINITE).apply {
        setAction(android.R.string.ok) {
        }
    }.show()
