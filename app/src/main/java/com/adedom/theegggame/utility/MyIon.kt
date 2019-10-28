package com.adedom.theegggame.utility

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.koushikdutta.ion.Ion
import java.io.File

class MyIon { // 25/7/62
    companion object {
        fun getIon(imageView: ImageView, image: String) {
            Ion.with(imageView).load(MyConnect.BASE_URL + "images/${image.trim()}")
        }

        fun getIon(context: Context, imagePath: String) {
            Ion.with(context)
                .load(MyConnect.BASE_URL + "upload-images.php")
                .setMultipartFile("file", File(imagePath.trim()))
                .asString()
        }

        fun getIon(context: Context, bitmap: Bitmap, image: Any): Bitmap {
            return Ion.with(context)
                .load(MyConnect.BASE_URL + "images/${image.toString().trim()}")
                .asBitmap()
                .get()
        }
    }
}