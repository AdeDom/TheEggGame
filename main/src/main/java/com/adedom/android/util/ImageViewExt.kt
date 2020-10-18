package com.adedom.android.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.adedom.android.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageCircle(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_image_profile
) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder))
        .circleCrop()
        .into(this)
}
