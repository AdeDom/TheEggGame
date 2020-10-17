package com.adedom.theegggame.util.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.adedom.theegggame.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageCircle(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_user_black
) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder))
        .circleCrop()
        .into(this)
}
