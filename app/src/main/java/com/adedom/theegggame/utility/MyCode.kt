package com.adedom.theegggame.utility

import android.content.Context

class MyCode {

    companion object {
        fun rndLatLng(latLng: Double, meter: Double): Double {
            var rnd = Math.random() / 100 // < 0.01
            rnd += meter / 100000 // 200 Meter
            val s = String.format("%.7f", rnd)

            var ll: Double
            ll = if ((0..1).random() == 0) {
                latLng + s.toDouble()
            } else {
                latLng - s.toDouble()
            }

            ll = String.format("%.7f", ll).toDouble()
            return ll
        }

        fun rndTeam(): String {
            return if ((0..1).random() == 0) {
                "A"
            } else {
                "B"
            }
        }

        fun checkIsEmpty(context: Context, object1: Any, message1: String): Boolean {
            if (object1.toString().trim().isEmpty()) {
                MyToast.showLong(context, message1)
                return true
            }
            return false
        }
    }
}