package com.adedom.theegggame.utility

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView

class MyIon {
    companion object {
        fun getIon(imageView: ImageView, image: String) {
//            Ion.with(imageView).load(MyConnect.BASE_URL + "images/${image.trim()}")
        }

        fun getIon(context: Context, imageUri: String) {
//            Ion.with(context)
//                .load(MyConnect.BASE_URL + "upload-images.php")
//                .setMultipartFile("file", File(imageUri.trim()))
//                .asString()
        }

        fun getIon(context: Context, bitmap: Bitmap, image: Any): Bitmap? {
//            return Ion.with(context)
//                .load(MyConnect.BASE_URL + "profile/${image.toString().trim()}")
//                .asBitmap()
//                .get()
            return null
        }
    }
}