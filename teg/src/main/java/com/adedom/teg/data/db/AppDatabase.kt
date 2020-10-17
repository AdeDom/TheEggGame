package com.adedom.teg.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adedom.teg.data.db.dao.PlayerInfoDao
import com.adedom.teg.data.db.entities.PlayerInfoEntity

@Database(entities = [PlayerInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPlayerInfoDao(): PlayerInfoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Teg.db"
        ).fallbackToDestructiveMigration().build()
    }

}
