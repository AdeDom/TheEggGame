package com.adedom.theegggame.util

import android.graphics.Bitmap
import android.widget.ImageView
import com.adedom.library.extension.loadCircle
import com.adedom.library.extension.resourceBitmap
import com.adedom.library.util.GoogleMapActivity
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

fun setImageProfile(
    image: String?,
    gender: String?,
    male: () -> Unit,
    female: () -> Unit,
    loadImage: () -> Unit
) {
    when {
        image == KEY_EMPTY && gender == KEY_MALE -> male.invoke()
        image == KEY_EMPTY && gender == KEY_FEMALE -> female.invoke()
        image != KEY_EMPTY -> loadImage.invoke()
    }
}

fun getItemBitmap(itemId: Int): Bitmap {
    return when (itemId) {
        1 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg)
        2 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_box)
        3 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_mystery_item)
        4 -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_egg_bonus)
        else -> GoogleMapActivity.sContext.resourceBitmap(R.drawable.ic_image_black)
    }
}

fun getLevel(level: Int?): String = "Level : $level"



