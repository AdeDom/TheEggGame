package com.adedom.android.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.adedom.android.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun Context?.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.setImageCircle(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_image_profile,
    onResourceReady: (Bitmap) -> Unit,
    onLoadCleared: (Bitmap) -> Unit,
) {
    if (!url.isNullOrBlank()) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .apply(RequestOptions.placeholderOf(placeholder))
            .circleCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    onResourceReady.invoke(convertLayoutMarkerPlayer(resource))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    } else {
        onLoadCleared.invoke(convertLayoutMarkerPlayer(null))
    }
}

private fun Context.convertLayoutMarkerPlayer(
    resource: Bitmap?,
    @LayoutRes layout: Int = R.layout.layout_marker_player,
): Bitmap {
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view: View = inflater.inflate(layout, null)
    resource?.let { view.findViewById<ImageView>(R.id.ivImageProfile).setImageBitmap(it) }
    val displayMetrics = DisplayMetrics()
    view.layoutParams =
        ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT)
    view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
    view.buildDrawingCache()
    val bitmap = Bitmap.createBitmap(
        view.measuredWidth,
        view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    view.draw(Canvas(bitmap))
    return bitmap
}

fun Context.convertLayoutMarkerItem(
    resource: Int,
    @LayoutRes layout: Int = R.layout.layout_marker_item,
): Bitmap {
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val view: View = inflater.inflate(layout, null)
    view.findViewById<ImageView>(R.id.ivImageItem).setImageResource(resource)
    val displayMetrics = DisplayMetrics()
    view.layoutParams =
        ViewGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT)
    view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
    view.buildDrawingCache()
    val bitmap = Bitmap.createBitmap(
        view.measuredWidth,
        view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    view.draw(Canvas(bitmap))
    return bitmap
}
