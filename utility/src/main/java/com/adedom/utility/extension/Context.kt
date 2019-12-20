package com.adedom.utility.extension

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.text.format.DateFormat
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adedom.utility.PREF_LOGIN
import com.adedom.utility.R
import java.util.*
import kotlin.collections.ArrayList

fun Context.getLocality(latitude: Double, longitude: Double): String {
    val list = Geocoder(this).getFromLocation(latitude, longitude, 1)
    return if (list[0].locality != null) {
        list[0].locality
    } else {
        "unknown"
    }
}

fun Context.getAdminArea(latitude: Double, longitude: Double): String {
    val list = Geocoder(this).getFromLocation(latitude, longitude, 1)
    return if (list[0].adminArea != null) {
        list[0].adminArea
    } else {
        "unknown"
    }
}

fun Context.getSubAdminArea(latitude: Double, longitude: Double): String {
    val list = Geocoder(this).getFromLocation(latitude, longitude, 1)
    return if (list[0].subAdminArea != null) {
        list[0].subAdminArea
    } else {
        "unknown"
    }
}

fun Context.spinnerLevel(): ArrayAdapter<Int> {
    val list = ArrayList<Int>()
    for (i in 0..99) list.add(i)
    val adapter = ArrayAdapter<Int>(
        this, android.R.layout.simple_spinner_item, list
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return adapter
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.failed() = Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show()

fun Context.completed() = Toast.makeText(this, R.string.completed, Toast.LENGTH_SHORT).show()

fun Context.getPrefLogin(key: String): String {
    val preferences = getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE)
    return preferences.getString(key, "")!!
}

fun Context.imageMarker(image: Int): Bitmap {
    return BitmapFactory.decodeResource(this.resources, image)
}

fun Context.datePickerDialog(date: (Int, Int, Int) -> Unit) {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val dpd = DatePickerDialog(
        this,
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            date.invoke(year, month + 1, dayOfMonth)
        },
        year,
        month,
        day
    )
    dpd.show()
}

fun Context.timePickerDialog(time: (Int, Int) -> Unit) {
    val c = Calendar.getInstance()
    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    val t = TimePickerDialog(
        this,
        TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            time.invoke(hourOfDay, minute)
        },
        hour,
        minute,
        DateFormat.is24HourFormat(this)
    )
    t.show()
}
