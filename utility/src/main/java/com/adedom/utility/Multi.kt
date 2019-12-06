package com.adedom.utility

const val ROOM = "room"
const val HEAD = "head"
const val TAIL = "tail"
const val TEAM_A = "A"
const val TEAM_B = "B"
const val READY = "ready"
const val UNREADY = "unready"

var ready = "unready"

fun ready(): String {
    ready = if (ready == "unready") "ready" else "unready"
    return ready
}

