package com.adedom.theegggame.ui.single

import android.graphics.Bitmap
import android.location.Location
import com.adedom.library.extension.resourceBitmap
import com.adedom.library.util.GoogleMapActivity
import com.adedom.library.util.KEY_EMPTY
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.ui.main.MainActivityViewModel
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class SingleActivityViewModel : BaseViewModel() {

    val single by lazy { arrayListOf<Single>() }
    var switchItem = GameSwitch.ON

    var itemBonus: Int = 0

    fun keepItemSingle(
        playerId: String?,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double
    ) = singleRepository.insertItemCollection(playerId, itemId, qty, latitude, longitude)

    fun titleItem(itemId: Int): String {
        var name = ""
        when (itemId) {
            1 -> name = "Experience point"
            2 -> name = "Mystery Box"
            3 -> name = "Mystery Item"
            4 -> name = "Bonus"
        }
        return name
    }

    fun detailItem(itemId: Int, values: Int): String {
        var name = ""
        when (itemId) {
            1 -> name = "Experience point : $values"
            2 -> name = "Egg I" // egg false
            3 -> name = "Egg II" // radius
            4 -> name = "Egg III" // stun
        }
        return name
    }

    fun checkRadius(latLng: LatLng, insertItem: (Int) -> Unit) {
        single.forEachIndexed { index, item ->
            val distance = FloatArray(1)
            Location.distanceBetween(
                latLng.latitude,
                latLng.longitude,
                item.latitude,
                item.longitude,
                distance
            )

            if (distance[0] < RADIUS_ONE_HUNDRED_METER) {
                insertItem.invoke(index)
                if (item.itemId == ITEM_EGG_NORMAL) itemBonus++ // Bonus
                markerItems[index].remove()
                single.removeAt(index)
                switchItem = GameSwitch.ON
                return
            }

            if (distance[0] > RADIUS_TWO_KILOMETER) {
                switchItem = GameSwitch.ON
                single.removeAt(index)
                return
            }
        }
    }

    fun rndMultiItem(latLng: LatLng) {
        if (single.size < MIN_ITEM) {
            val numItem = (MIN_ITEM..MAX_ITEM).random()
            for (i in 0 until numItem) {
                val item = Single(
                    (1..3).random(),
                    rndLatLng(latLng.latitude),
                    rndLatLng(latLng.longitude)
                )
                single.add(item)
            }
        }
    }

    private fun rndLatLng(latLng: Double): Double {
        var rnd = Math.random() / 100 // < 0.01
        rnd += RADIUS_TWO_HUNDRED_METER / 100000 // 200 Meter
        val s = String.format("%.7f", rnd)
        var ll: Double = if ((0..1).random() == 0) latLng + s.toDouble() else latLng - s.toDouble()
        ll = String.format("%.7f", ll).toDouble()
        return ll
    }

    fun getItemValues(i: Int): Pair<Int, Int> {
        var myItem = single[i].itemId // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

        val timeStart = MainActivityViewModel.timeStamp
        val timeNow = System.currentTimeMillis() / 1000
        if (timeNow > timeStart + TIME_FIVE_MINUTE.toLong()) values *= 2 // Multiply 2

        when (myItem) {
            2 -> { // mystery box
                myItem = (1..2).random() // random exp and item*/
                values += (1..20).random() // mystery box + 20 point.
                if (myItem == 1) {
                    itemBonus++
                } else {
                    myItem = (2..4).random() // random item
                    values = 1
                }
            }
            3 -> { // item
                myItem = (2..4).random()
                values = 1
            }
            4 -> {// Bonus
                myItem = 1
                values = (300..399).random()
            }
        }
        return Pair(myItem, values)
    }

    fun rndItemBonus(latLng: LatLng) {
        if (itemBonus % 3 == 0 && itemBonus != 0) {
            itemBonus = 0
            for (i in 1..MAX_ITEM) {
                val item = Single(4, rndLatLng(latLng.latitude), rndLatLng(latLng.longitude))
                single.add(item)
            }
        }
    }

    companion object {
        val markerItems by lazy { arrayListOf<Marker>() }

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
    }
}

