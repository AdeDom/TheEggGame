package com.adedom.utility

import java.sql.Connection

class Execute(
    private val connection: Connection? = null
) {

    fun execute(sql: String): ExecuteCommit {
        return ExecuteCommit(this.connection, sql)
    }

}