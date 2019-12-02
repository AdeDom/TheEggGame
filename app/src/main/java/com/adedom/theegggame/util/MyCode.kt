package com.adedom.theegggame.util

class MyCode {

    companion object {
        fun rndTeam(): String {
            return if ((0..1).random() == 0) {
                "A"
            } else {
                "B"
            }
        }
    }
}