package com.adedom.theegggame.data

private const val HOST = "192.168.43.22"
const val BASE_URL = "http://$HOST/the-egg-game/api/"

//const val BASE_URL = "http://pathiphon.000webhostapp.com/api/"

//const val BASE_URL = "http://tm.dru.ac.th/apidb/upload/api/"

fun imageUrl(image: String) = "${BASE_URL}profiles/$image"
