package com.adedom.utility

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.ImageView
import com.adedom.utility.data.BASE_URL
import com.adedom.utility.extension.loadProfile
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.*

const val COMPLETED = "completed"
const val FAILED = "failed"

const val PLAYER = "player"
const val ONLINE = "online"
const val OFFLINE = "offline"

const val PREF_LOGIN = "PREF_LOGIN"
const val PLAYER_ID = "player_id"
const val USERNAME = "username"
const val EMPTY = "empty"
const val MALE = "M"
const val FEMALE = "F"

const val DATE = "date"
const val TIME = "time"

var timeStamp: Long = 0
var rndkey: String = ""

var countExit = 0

fun checkPassword(editText1: EditText, editText2: EditText, error: String): Boolean {
    val edt1 = editText1.text.toString().trim()
    val edt2 = editText2.text.toString().trim()
    if (edt1 != edt2) {
        editText2.requestFocus()
        editText2.error = error
        return true
    }
    return false
}

fun getDateTime(dateTime: String): String {
    return if (dateTime == DATE) {
        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            .format(Calendar.getInstance().time)
    } else {
        SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            .format(Calendar.getInstance().time)
    }
}

fun loadBitmap(
    context: Context,
    image: String,
    googleMap: GoogleMap,
    latLng: LatLng,
    title: String,
    snippet: Int
) {
    Glide.with(context)
        .asBitmap()
        .load("$BASE_URL../profiles/${image}")
        .circleCrop()
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                setMarker(
                    googleMap,
                    latLng,
                    BitmapDescriptorFactory.fromBitmap(resource),
                    title,
                    getLevel(snippet)
                )
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                setMarker(
                    googleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    title,
                    getLevel(snippet)
                )
            }
        })
}

fun loadBitmapList(
    context: Context,
    image: String,
    googleMap: GoogleMap,
    latLng: LatLng,
    title: String,
    snippet: Int
) {
    Glide.with(context)
        .asBitmap()
        .load("$BASE_URL../profiles/${image}")
        .circleCrop()
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                setListMarker(
                    markerPlayers,
                    googleMap,
                    latLng,
                    BitmapDescriptorFactory.fromBitmap(resource),
                    title,
                    getLevel(snippet)
                )
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                setListMarker(
                    markerPlayers,
                    googleMap,
                    latLng,
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_player),
                    title,
                    getLevel(snippet)
                )
            }
        })
}

fun setImageProfile(ivImage: ImageView, image: String, gender: String) {
    when {
        image == EMPTY && gender == MALE -> {
            ivImage.setImageResource(R.drawable.ic_player)
        }
        image == EMPTY && gender == FEMALE -> {
            ivImage.setImageResource(R.drawable.ic_player_female)
        }
        image != EMPTY -> ivImage.loadProfile(image)
    }
}
