package com.adedom.admin.util

import android.widget.ImageView
import com.adedom.admin.R
import com.adedom.admin.data.imageUrl
import com.adedom.library.extension.loadCircle
import com.adedom.library.util.KEY_EMPTY

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

