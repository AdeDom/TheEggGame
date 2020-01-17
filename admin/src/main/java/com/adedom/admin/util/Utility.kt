package com.adedom.admin.util

import android.widget.ImageView
import com.adedom.admin.R
import com.adedom.admin.data.imageUrl
import com.adedom.library.data.KEY_DATE
import com.adedom.library.data.KEY_EMPTY
import com.adedom.library.data.KEY_TIME
import com.adedom.library.extension.loadCircle
import com.adedom.library.util.getDateTime

const val DATE_BEGIN = "0000-00-00"
const val TIME_BEGIN = "00:00"

val DATE_NOW = getDateTime(KEY_DATE)
val TIME_NOW = getDateTime(KEY_TIME)

var name = ""
var spinnerIndexStart = 0
var spinnerIndexEnd = 98
var isCheckOnline = true
var isCheckOffline = true
var isCheckMale = true
var isCheckFemale = true
var dateBegin = DATE_BEGIN
var timeBegin = TIME_BEGIN
var dateEnd = DATE_NOW
var timeEnd = TIME_NOW

fun setImageProfile(ivImage: ImageView, image: String, gender: String) {
    when {
        image == KEY_EMPTY && gender == KEY_MALE -> {
            ivImage.setImageResource(R.drawable.ic_player)
        }
        image == KEY_EMPTY && gender == KEY_FEMALE -> {
            ivImage.setImageResource(R.drawable.ic_player_female)
        }
        image != KEY_EMPTY -> ivImage.loadCircle(imageUrl(image))
    }
}

