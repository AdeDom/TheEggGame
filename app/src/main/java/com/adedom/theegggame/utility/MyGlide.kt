package com.adedom.theegggame.utility

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MyGlide {

    companion object{
        fun getGlide(context: Context, imageView: ImageView, image: Any) {
            Glide.with(context)
                .load(MyConnect.BASE_URL + "images/$image")
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }
    }
}