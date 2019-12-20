package com.adedom.utility.extension

import android.widget.ImageView
import com.adedom.utility.R
import com.adedom.utility.data.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadCircle(url: String) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_player))
        .circleCrop()
        .into(this)
}

fun ImageView.loadProfile(image: String) {
    Glide.with(this)
        .load("$BASE_URL../profiles/$image")
        .apply(RequestOptions.placeholderOf(R.drawable.ic_player))
        .circleCrop()
        .into(this)
}