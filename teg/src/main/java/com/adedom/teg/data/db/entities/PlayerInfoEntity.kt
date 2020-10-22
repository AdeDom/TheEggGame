package com.adedom.teg.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adedom.teg.data.db.DatabaseConstant

@Entity(tableName = DatabaseConstant.PLAYER_INFO)
data class PlayerInfoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = DatabaseConstant.PLAYER_ID) val playerId: String = "",
    @ColumnInfo(name = DatabaseConstant.USERNAME) val username: String? = null,
    @ColumnInfo(name = DatabaseConstant.NAME) val name: String? = null,
    @ColumnInfo(name = DatabaseConstant.IMAGE) val image: String? = null,
    @ColumnInfo(name = DatabaseConstant.LEVEL) val level: Int? = null,
    @ColumnInfo(name = DatabaseConstant.STATE) val state: String? = null,
    @ColumnInfo(name = DatabaseConstant.GENDER) val gender: String? = null,
    @ColumnInfo(name = DatabaseConstant.BIRTH_DATE) val birthDate: String? = null,
)
