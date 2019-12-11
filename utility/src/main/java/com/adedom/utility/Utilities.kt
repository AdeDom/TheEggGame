package com.adedom.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

const val DATE = "date"
const val TIME = "time"

var timeStamp: Long = 0
var randomKey: String = ""

var countExit = 0

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.failed() = Toast.makeText(this, R.string.failed, Toast.LENGTH_LONG).show()

fun Context.completed() = Toast.makeText(this, R.string.completed, Toast.LENGTH_SHORT).show()

fun Activity.login(
    c: Class<*>,
    playerId: String = EMPTY,
    username: String? = null
) {
    baseContext.getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE).edit()
        .putString(PLAYER_ID, playerId)
        .putString(USERNAME, username)
        .apply()
    this.finishAffinity()
    this.startActivity(Intent(baseContext, c).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
}

fun Context.getPrefLogin(key: String): String {
    val preferences = getSharedPreferences(PREF_LOGIN, Context.MODE_PRIVATE)
    return preferences.getString(key, "")!!
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ImageView.loadProfile(image: String) {
    Glide.with(this)
        .load("$BASE_URL../profiles/$image")
        .circleCrop()
        .into(this)
}

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

fun Context.imageMarker(image: Int): Bitmap {
    return BitmapFactory.decodeResource(this.resources, image)
}

fun getDateTime(dateTime: String): String {
    return if (dateTime == DATE) {
        SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
            .format(Calendar.getInstance().time)
    } else {
        SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
            .format(Calendar.getInstance().time)
    }
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

fun AlertDialog.Builder.dialog(view: View, icon: Int, title: Int) =
    this.setView(view).setIcon(icon).setTitle(title).create()

fun EditText.failed(message: String = "") {
    this.also {
        it.requestFocus()
        it.error = message
    }
}

fun RecyclerView.recyclerVertical(rv: (RecyclerView) -> Unit) {
    this.also {
        it.layoutManager = LinearLayoutManager(context)
        it.setHasFixedSize(true)
        rv.invoke(it)
    }
}

fun RecyclerView.recyclerGrid(rv: (RecyclerView) -> Unit) {
    this.also {
        it.layoutManager = GridLayoutManager(context, 2)
        it.addItemDecoration(ItemDecoration(2, ItemDecoration.dpToPx(10, resources), true))
        rv.invoke(it)
    }
}

fun AlertDialog.Builder.exitDialog(negative: () -> Unit) {
    this.setIcon(R.drawable.ic_exit_black)
        .setTitle(R.string.exit)
        .setPositiveButton(R.string.no) { dialog, which -> dialog.dismiss() }
        .setNegativeButton(R.string.yes) { dialog, which ->
            negative.invoke()
        }.show()
}

fun Activity.exitMain() {
    if (countExit > 0) this.finishAffinity()
    countExit++
    Handler().postDelayed({ countExit = 0 }, 2000)
    baseContext.toast(R.string.on_back_pressed)
}

fun AppCompatActivity.toolbar(toolbar: Toolbar, title: String) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
}
