package com.adedom.utility

object Pathiphon {
    private val HOST = "192.168.43.22"
    val BASE_URL = "http://$HOST/the-egg-game/api/"

//    val BASE_URL = "http://pathiphon.hostingerapp.com/api/"


//    companion object {
//        private val USERNAME = "root"
//        private val PASSWORD = "abc456"
//        private val DB_NAME = "the_egg_game"
//        fun connection(): Connection? {
//            return connection(HOST, USERNAME, PASSWORD, DB_NAME)
//        }
//
//        fun connection(
//            ip: String,
//            username: String,
//            password: String,
//            dbName: String
//        ): Connection? {
//            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
//
//            try {
//                Class.forName("com.mysql.jdbc.Driver")
//                val url = "jdbc:mysql://$ip/$dbName?useUnicode=true&characterEncoding=utf-8"
//                return DriverManager.getConnection(url, username, password)
//            } catch (e: ClassNotFoundException) {
//                e.printStackTrace()
//            } catch (e: SQLException) {
//                e.printStackTrace()
//            }
//
//            return null
//        }
//
//        @Deprecated("")
//        fun connection(connection: Connection): Execute {
//            return Execute(connection)
//        }
//
//        @Deprecated("")
//        fun with(connection: Connection): Call {
//            return Call(connection)
//        }
//
//        fun execute(sql: String): ExecuteCommit {
//            return ExecuteCommit(connection(), sql)
//        }
//
//        fun call(storedProcedureName: String): CallCommit {
//            return CallCommit(connection(), storedProcedureName)
//        }
//    }
}