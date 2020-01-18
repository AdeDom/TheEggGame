package com.adedom.theegggame.ui.single

import android.location.Location
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class SingleActivityViewModel : BaseViewModel() {

    val single by lazy { arrayListOf<Single>() }
    var switchItem = GameSwitch.ON

    fun keepItemSingle(
        playerId: String,
        itemId: Int,
        qty: Int,
        latitude: Double,
        longitude: Double,
        date: String,
        time: String
    ) = singleRepository.insertItemCollection(
        playerId,
        itemId,
        qty,
        latitude,
        longitude,
        date,
        time
    )

    fun titleItem(itemId: Int): String {
        var name = ""
        when (itemId) {
            1 -> name = "Experience point"
            2 -> name = "Mystery Box"
            3 -> name = "Mystery Item"
        }
        return name
    }

    fun detailItem(itemId: Int, values: Int): String {
        var name = ""
        when (itemId) {
            1 -> name = "Experience point : $values"
            2 -> name = "Egg I" // egg false
            3 -> name = "Egg II" // hide egg + radius
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

    fun getItemValues(i: Int, timeStamp: Long): Pair<Int, Int> {
        var myItem = single[i].itemId // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

        val timeNow = System.currentTimeMillis() / 1000
        if (timeNow > timeStamp + TIME_FIFTEEN_MINUTE.toLong()) values *= 2 // Multiply 2

        when (myItem) {
            2 -> { // mystery box
                myItem = (1..2).random() // random exp and item*/
                values += (1..20).random() // mystery box + 20 point.
                if (myItem == 2) {
                    myItem = (2..4).random() // random item
                    values = 1
                }
            }
            3 -> { // item
                myItem = (2..4).random()
                values = 1
            }
        }
        return Pair(myItem, values)
    }

    companion object{
        val markerItems by lazy { arrayListOf<Marker>() }
    }
}

