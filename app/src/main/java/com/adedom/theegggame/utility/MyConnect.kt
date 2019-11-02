package com.adedom.theegggame.utility

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager

class MyConnect { // 25/7/62

    companion object {
        private const val TAG = "MyConnect"
        private const val IP = "192.168.43.22"
        private const val DB_NAME = "the_egg_game"
        private const val USERNAME = "root"
        private const val PASSWORD = "abc456"
        const val BASE_URL = "http://$IP/the-egg-game/"

        fun conn(): Connection {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())

            Class.forName("com.mysql.jdbc.Driver")
            val url = "jdbc:mysql://$IP/$DB_NAME?useUnicode=true&characterEncoding=utf-8"
            return DriverManager.getConnection(url, USERNAME, PASSWORD)
        }

        fun executeQuery(sql: String) {
            conn().createStatement().executeUpdate(sql)
        }

        fun executeQuery(sql: String, rs: MyResultSet) {
            rs.onResponse(conn().createStatement().executeQuery(sql))
        }
    }
}
