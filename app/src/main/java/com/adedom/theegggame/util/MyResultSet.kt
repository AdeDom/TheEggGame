package com.adedom.theegggame.util

import java.sql.ResultSet

interface MyResultSet {
    fun onResponse(rs: ResultSet)
}