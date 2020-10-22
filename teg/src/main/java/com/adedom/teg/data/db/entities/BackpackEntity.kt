package com.adedom.teg.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adedom.teg.data.db.DatabaseConstant

@Entity(tableName = DatabaseConstant.BACKPACK)
data class BackpackEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DatabaseConstant.BACKPACK_ID) val backpackId: Int = 0,
    @ColumnInfo(name = DatabaseConstant.EGG_I) val eggI: Int? = null,
    @ColumnInfo(name = DatabaseConstant.EGG_II) val eggII: Int? = null,
    @ColumnInfo(name = DatabaseConstant.EGG_III) val eggIII: Int? = null,
)
