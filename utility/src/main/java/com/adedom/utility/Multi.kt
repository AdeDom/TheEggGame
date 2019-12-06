package com.adedom.utility

var ready = "unready"

fun ready(): String {
    ready = if (ready == "unready") "ready" else "unready"
    return ready
}

