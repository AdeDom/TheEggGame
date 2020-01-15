package com.adedom.admin.util

import com.adedom.utility.DATE
import com.adedom.utility.TIME
import com.adedom.utility.getDateTime

const val DATE_BEGIN = "0000-00-00"
const val TIME_BEGIN = "00:00"

val DATE_NOW = getDateTime(DATE)
val TIME_NOW = getDateTime(TIME)

var name = ""
var spinnerIndexStart = 0
var spinnerIndexEnd = 98
var isCheckOnline = true
var isCheckOffline = true
var isCheckMale = true
var isCheckFemale = true
var dateBegin = DATE_BEGIN
var timeBegin = TIME_BEGIN
var dateEnd = DATE_NOW
var timeEnd = TIME_NOW
