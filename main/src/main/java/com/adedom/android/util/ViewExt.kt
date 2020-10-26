package com.adedom.android.util

import android.view.View
import androidx.annotation.CheckResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.snackbar(message: String?) =
    Snackbar.make(this, message.toString(), Snackbar.LENGTH_INDEFINITE).apply {
        setAction(android.R.string.ok) {
        }
    }.show()

@CheckResult
@ExperimentalCoroutinesApi
fun View.clicks(): Flow<Unit> {
    return callbackFlow {
        setOnClickListener { offer(Unit) }
        awaitClose { setOnClickListener(null) }
    }
}

fun View.setVisibility(isVisibility: Boolean) {
    this.visibility = if (isVisibility) View.VISIBLE else View.INVISIBLE
}
