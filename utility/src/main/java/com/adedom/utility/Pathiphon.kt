package com.adedom.utility

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Pathiphon {

    companion object {
        private val HOST = "192.168.43.22"
        private val USERNAME = "root"
        private val PASSWORD = "abc456"
        private val DB_NAME = "the_egg_game"
        val BASE_URL = "http://$HOST/the-egg-game/php-script-file/"
//        val BASE_URL = "http://pathiphon.000webhostapp.com/php-script-file/"

        fun connection(): Connection? {
            return connection(HOST, USERNAME, PASSWORD, DB_NAME)
        }

        fun connection(
            ip: String,
            username: String,
            password: String,
            dbName: String
        ): Connection? {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

            try {
                Class.forName("com.mysql.jdbc.Driver")
                val url = "jdbc:mysql://$ip/$dbName?useUnicode=true&characterEncoding=utf-8"
                return DriverManager.getConnection(url, username, password)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: SQLException) {
                e.printStackTrace()
            }

            return null
        }

        @Deprecated("")
        fun connection(connection: Connection): Execute {
            return Execute(connection)
        }

        @Deprecated("")
        fun with(connection: Connection): Call {
            return Call(connection)
        }

        fun execute(sql: String): ExecuteCommit {
            return ExecuteCommit(connection(), sql)
        }

        fun call(storedProcedureName: String): CallCommit {
            return CallCommit(connection(), storedProcedureName)
        }
    }
}