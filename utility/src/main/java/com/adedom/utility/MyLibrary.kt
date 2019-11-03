package com.adedom.utility

import android.content.Context
import android.widget.EditText
import android.widget.Toast

class MyLibrary {

    companion object{
        fun completed(context: Context) {
            Toast.makeText(context, R.string.completed, Toast.LENGTH_SHORT).show()
        }

        fun failed(context: Context) {
            Toast.makeText(context, R.string.failed, Toast.LENGTH_LONG).show()
        }

        fun with(context: Context): Utility {
            return Utility(context)
        }

        fun isEmpty(editText: EditText, error: String): Boolean {
            if (editText.text.toString().trim().isEmpty()) {
                editText.requestFocus()
                editText.error = error
                return true
            }
            return false
        }
    }
}