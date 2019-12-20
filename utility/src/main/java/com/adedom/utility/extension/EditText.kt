package com.adedom.utility.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.isEmpty(error: String): Boolean {
    if (this.text.toString().trim().isEmpty()) {
        this.requestFocus()
        this.error = error
        return true
    }
    return false
}

fun EditText.checkLess4(error: String): Boolean {
    if (this.text.toString().trim().length < 4) {
        this.requestFocus()
        this.error = error
        return true
    }
    return false
}

fun EditText.getContent(): String = this.text.toString().trim()

fun EditText.textChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(editable: CharSequence?, p1: Int, p2: Int, p3: Int) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.failed(message: String = "") {
    this.apply {
        requestFocus()
        error = message
    }
}
