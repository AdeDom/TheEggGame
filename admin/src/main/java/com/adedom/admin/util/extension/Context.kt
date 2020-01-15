package com.adedom.admin.util.extension

import android.content.Context
import android.widget.ArrayAdapter

fun Context.spinnerLevel(): ArrayAdapter<Int> {
    val list = ArrayList<Int>()
    for (i in 1..99) list.add(i)
    val adapter = ArrayAdapter<Int>(
        this, android.R.layout.simple_spinner_item, list
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return adapter
}