package com.adedom.teg.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.teg.data.db.entities.PlayerInfoEntity

@Dao
interface PlayerInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayerInfo(playerInfo: PlayerInfoEntity)

    @Query("SELECT * FROM player_info")
    suspend fun getDbPlayerInfo(): PlayerInfoEntity?

    @Query("SELECT * FROM player_info")
    fun getDbPlayerInfoLiveData(): LiveData<PlayerInfoEntity>

    @Query("DELETE FROM player_info")
    suspend fun deletePlayerInfo()

}
