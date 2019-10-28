package com.adedom.theegggame.utility

import java.sql.ResultSet

interface MyResultSet {
    fun onResponse(rs: ResultSet)
}