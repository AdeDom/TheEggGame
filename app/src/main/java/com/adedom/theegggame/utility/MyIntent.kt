package com.adedom.theegggame.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable

class MyIntent: Activity() {

    fun getIntent(context: Context, goClass: Class<*>) {
        context.startActivity(Intent(context, goClass))
    }

    fun getIntent(context: Context, goClass: Class<*>, parcelable: Parcelable) {
        val intent = Intent(context, goClass)
        intent.putExtra("values1", parcelable)
        context.startActivity(intent)
    }

    fun getIntent(context: Context, goClass: Class<*>, object1: Any, object2: Any, object3: Any, object4: Any) {
        val intent = Intent(context, goClass)
        intent.putExtra("values1", object1.toString().trim())
        intent.putExtra("values2", object2.toString().trim())
        intent.putExtra("values3", object3.toString().trim())
        intent.putExtra("values4", object4.toString().trim())
        context.startActivity(intent)
    }
}