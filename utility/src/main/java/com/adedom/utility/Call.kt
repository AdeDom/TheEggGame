package com.adedom.utility

import java.sql.Connection

class Call(
    private val connection: Connection? = null
) {

    fun call(storedProcedureName: String): CallCommit {
        return CallCommit(this.connection, storedProcedureName)
    }

}