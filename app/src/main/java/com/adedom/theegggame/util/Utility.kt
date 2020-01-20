package com.adedom.theegggame.util

import android.widget.ImageView
import com.adedom.library.extension.loadCircle
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.R
import com.adedom.theegggame.data.imageUrl

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

fun getLevel(level: Int?): String = "Level : $level"



