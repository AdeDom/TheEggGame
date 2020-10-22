package com.adedom.teg.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adedom.teg.data.db.entities.BackpackEntity

@Dao
interface BackpackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBackpack(backpack: BackpackEntity)

    @Query("SELECT * FROM backpack")
    suspend fun getDbBackpack(): BackpackEntity?

    @Query("SELECT * FROM backpack")
    fun getDbBackpackLiveData(): LiveData<BackpackEntity>

    @Query("DELETE FROM backpack")
    suspend fun deleteBackpack()

}
