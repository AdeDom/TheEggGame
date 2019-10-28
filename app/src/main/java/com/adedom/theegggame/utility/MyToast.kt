package com.adedom.theegggame.utility

import android.content.Context
import android.widget.Toast

class MyToast {

    companion object {
        fun showLong(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun showShort(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}