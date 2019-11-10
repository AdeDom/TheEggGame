package com.adedom.utility

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.koushikdutta.ion.Ion
import java.io.File

class Utility(
    val context: Context? = null
) {

    fun showLong(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showLong(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showShort(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showShort(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun ionUpload(imageUri: String) {
        Ion.with(context)
            .load(Pathiphon.BASE_URL + "upload-profile.php")
            .setMultipartFile("file", File(imageUri.trim()))
            .asString()
    }

    fun glide(uri: String, imageView: ImageView) {
        Glide.with(context!!)
            .load(uri)
            .circleCrop()
            .into(imageView)
    }

    fun glideProfile(image: String, imageView: ImageView) {
        Glide.with(context!!)
            .load(Pathiphon.BASE_URL + "profiles/$image")
            .circleCrop()
            .into(imageView)
    }

}