package com.adedom.utility

import android.widget.ImageView
import com.adedom.library.extension.loadCircle
import com.adedom.utility.data.BASE_URL
import java.text.SimpleDateFormat
import java.util.*

const val COMPLETED = "completed"
const val FAILED = "failed"

const val PLAYER = "player"
const val ONLINE = "online"
const val OFFLINE = "offline"

const val PLAYER_ID = "player_id"
const val USERNAME = "username"
const val EMPTY = "empty"
const val MALE = "M"
const val FEMALE = "F"

const val DATE = "date"
const val TIME = "time"

var timeStamp: Long = 0
var rndkey: String = ""

fun getDateTime(dateTime: String): String {
    return if (dateTime == DATE) {
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .format(Calendar.getInstance().time)
    } else {
        SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            .format(Calendar.getInstance().time)
    }
}

fun setImageProfile(ivImage: ImageView, image: String, gender: String) {
    when {
        image == EMPTY && gender == MALE -> {
            ivImage.setImageResource(R.drawable.ic_player)
        }
        image == EMPTY && gender == FEMALE -> {
            ivImage.setImageResource(R.drawable.ic_player_female)
        }
        image != EMPTY -> ivImage.loadCircle("$BASE_URL../profiles/$image")
    }
}

