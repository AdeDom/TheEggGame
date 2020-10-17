package com.adedom.theegggame.ui.single

import com.adedom.library.extension.readPrefFile
import com.adedom.library.extension.writePrefFile
import com.adedom.library.util.GoogleMapActivity.Companion.sContext
import com.adedom.library.util.GoogleMapActivity.Companion.sLatLng
import com.adedom.library.util.KEY_EMPTY
import com.adedom.library.util.distanceBetween
import com.adedom.theegggame.R
import com.adedom.theegggame.data.models.Single
import com.adedom.theegggame.util.*
import com.adedom.theegggame.util.extension.playSoundKeep
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

class SingleActivityViewModel : BaseViewModel() {

    private val single by lazy { arrayListOf<Single>() }
    private val markerItems by lazy { arrayListOf<Marker>() }
    var switchItem = GameSwitch.ON
    var itemBonus: Int = 0
    lateinit var latLngBot: LatLng

//    fun keepItemSingle(
//        playerId: String?,
//        itemId: Int,
//        qty: Int,
//        latitude: Double,
//        longitude: Double
//    ) = singleRepository.insertItemCollection(playerId, itemId, qty, latitude, longitude)
//
//    fun fetchBackpack(playerId: String?) = singleRepository.fetchBackpack(playerId)

    fun checkRadius(insertItem: (Int) -> Unit) {
        single.forEachIndexed { index, item ->
            val distance = distanceBetween(
                sLatLng.latitude,
                sLatLng.longitude,
                item.latitude,
                item.longitude
            )

            if (distance < RADIUS_ONE_HUNDRED_METER) {
                insertItem.invoke(index)
                if (item.itemId == ITEM_EGG_NORMAL) itemBonus++ // Bonus
                markerItems[index].remove()
                single.removeAt(index)
                switchItem = GameSwitch.ON
                sContext.playSoundKeep() // sound
                return
            }

            if (distance > RADIUS_TWO_KILOMETER) {
                switchItem = GameSwitch.ON
                single.removeAt(index)
                return
            }
        }
    }

    fun rndMultiItem() {
        if (single.size < MIN_ITEM) {
            val numItem = (MIN_ITEM..MAX_ITEM).random()
            for (i in 0 until numItem) {
                val (lat, lng) = rndLatLng(sLatLng)
                val item = Single((1..3).random(), lat, lng)
                single.add(item)
            }
        }
    }

    fun itemMessages(itemId: Int, values: Int): String {
        return when (itemId) {
            1 -> sContext.resources.getString(R.string.experience_point, values)
            2 -> sContext.resources.getString(R.string.egg_i, values) // egg false
            3 -> sContext.resources.getString(R.string.egg_ii, values) // radius
            4 -> sContext.resources.getString(R.string.egg_iii, values) // stun
            else -> KEY_EMPTY
        }
    }

    fun getItemValues(i: Int): Pair<Int, Int> {
        var myItem = single[i].itemId // item Id
        var values = (Math.random() * 100).toInt() + 20 // number values && minimum 20

        val timeStart = timeStamp
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

    fun rndItemBonus() {
        if (itemBonus % 3 == 0 && itemBonus != 0) {
            itemBonus = 0
            for (i in 1..MAX_ITEM) {
                val (lat, lng) = rndLatLng(sLatLng)
                val item = Single(4, lat, lng)
                single.add(item)
            }
            val (lat, lng) = rndLatLng(sLatLng)
            latLngBot = LatLng(lat, lng)
            switchItem = GameSwitch.ON

            if (sContext.readPrefFile(KEY_MISSION_SINGLE_GAME) == KEY_MISSION_UNSUCCESSFUL) {
                sContext.writePrefFile(
                    KEY_MISSION_SINGLE_GAME,
                    KEY_MISSION_SUCCESSFUL
                )
            }
        }
    }

    fun checkItem(item: (ArrayList<Single>, ArrayList<Marker>) -> Unit) {
        if (switchItem == GameSwitch.ON) {
            switchItem = GameSwitch.OFF
            item.invoke(single, markerItems)
        }
    }

    fun bot(bot: (LatLng) -> Unit) {
        val bonus = single.filter { it.itemId == 4 }
        if (bonus.count() == 0) return

        var botLat = latLngBot.latitude
        var botLng = latLngBot.longitude

        // (1)
        val alf = ArrayList<Float>()
        single.forEach { alf.add(distanceBetween(botLat, botLng, it.latitude, it.longitude)) }

        // (2)
        val nearIndex = alf.indexOf(alf.min())

        // (3)
        val nearLat = single[nearIndex].latitude
        val nearLng = single[nearIndex].longitude

        alf.clear()
        val d1 =
            distanceBetween(nearLat, nearLng, botLat + KEY_DISTANCE_BOT, botLng + KEY_DISTANCE_BOT)
        val d2 =
            distanceBetween(nearLat, nearLng, botLat + KEY_DISTANCE_BOT, botLng - KEY_DISTANCE_BOT)
        val d3 =
            distanceBetween(nearLat, nearLng, botLat - KEY_DISTANCE_BOT, botLng + KEY_DISTANCE_BOT)
        val d4 =
            distanceBetween(nearLat, nearLng, botLat - KEY_DISTANCE_BOT, botLng - KEY_DISTANCE_BOT)
        alf.add(d1)
        alf.add(d2)
        alf.add(d3)
        alf.add(d4)

        // (4)
        when (alf.indexOf(alf.min())) {
            0 -> {
                botLat += KEY_DISTANCE_BOT
                botLng += KEY_DISTANCE_BOT
            }
            1 -> {
                botLat += KEY_DISTANCE_BOT
                botLng -= KEY_DISTANCE_BOT
            }
            2 -> {
                botLat -= KEY_DISTANCE_BOT
                botLng += KEY_DISTANCE_BOT
            }
            3 -> {
                botLat -= KEY_DISTANCE_BOT
                botLng -= KEY_DISTANCE_BOT
            }
        }

        // (5)
        botLat = String.format(KEY_LATLNG, botLat).toDouble()
        botLng = String.format(KEY_LATLNG, botLng).toDouble()
        latLngBot = LatLng(botLat, botLng)

        bot.invoke(latLngBot)

        // (6)
        val destroyed = distanceBetween(nearLat, nearLng, botLat, botLng)
        if (destroyed < RADIUS_ONE_HUNDRED_METER) {
            single.removeAt(nearIndex)
            switchItem = GameSwitch.ON
        }

    }

    companion object {
        var markerPlayer: Marker? = null
        var markerBot: Marker? = null
        var circlePlayer: Circle? = null
    }

}
