package com.adedom.android.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.adedom.android.R
import com.adedom.teg.util.TegConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun Context?.toast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@SuppressLint("InflateParams", "SetTextI18n")
fun Context?.toastTegSingle(
    @DrawableRes image: Int,
    qty: Int?,
    message: String?,
    duration: Int = Toast.LENGTH_LONG
) {
    val view: View = LayoutInflater.from(this).inflate(R.layout.layout_toast, null)

    view.apply {
        findViewById<ImageView>(R.id.ivImageItem).setImageResource(image)
        findViewById<TextView>(R.id.tvQty).text = "($qty)"
        findViewById<TextView>(R.id.tvMessage).text = message
    }

    Toast(this).apply {
        setGravity(Gravity.TOP, 0, 150)
        setDuration(duration)
        setView(view)
        show()
    }
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

suspend fun Context.setImageCircle(
    url: String?,
    gender: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_image_profile,
    @LayoutRes layout: Int = R.layout.layout_marker_player,
) = suspendCancellableCoroutine<Bitmap> { continuation ->
    if (!url.isNullOrBlank()) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .apply(RequestOptions.placeholderOf(placeholder))
            .circleCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val inflater =
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view: View = inflater.inflate(layout, null)
                    view.findViewById<ImageView>(R.id.ivImageProfile).setImageBitmap(resource)
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

                    continuation.resume(bitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    } else {
        if (gender == TegConstant.GENDER_MALE) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(layout, null)
            view.findViewById<ImageView>(R.id.ivImageProfile)
                .setImageResource(R.drawable.ic_image_profile_male)
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

            continuation.resume(bitmap)
        } else {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(layout, null)
            view.findViewById<ImageView>(R.id.ivImageProfile)
                .setImageResource(R.drawable.ic_image_profile_female)
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

            continuation.resume(bitmap)
        }
    }
}
