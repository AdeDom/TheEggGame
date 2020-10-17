package com.adedom.teg.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adedom.teg.data.db.DatabaseConstant
import com.google.gson.annotations.SerializedName

@Entity(tableName = DatabaseConstant.PLAYER_INFO)
data class PlayerInfoEntity(
    @PrimaryKey(autoGenerate = false)
    @SerializedName(DatabaseConstant.PLAYER_ID) @ColumnInfo(name = DatabaseConstant.PLAYER_ID) val playerId: String = "",
    @SerializedName(DatabaseConstant.USERNAME) @ColumnInfo(name = DatabaseConstant.USERNAME) val username: String? = null,
    @SerializedName(DatabaseConstant.NAME) @ColumnInfo(name = DatabaseConstant.NAME) val name: String? = null,
    @SerializedName(DatabaseConstant.IMAGE) @ColumnInfo(name = DatabaseConstant.IMAGE) val image: String? = null,
    @SerializedName(DatabaseConstant.LEVEL) @ColumnInfo(name = DatabaseConstant.LEVEL) val level: Int? = null,
    @SerializedName(DatabaseConstant.STATE) @ColumnInfo(name = DatabaseConstant.STATE) val state: String? = null,
    @SerializedName(DatabaseConstant.GENDER) @ColumnInfo(name = DatabaseConstant.GENDER) val gender: String? = null,
    @SerializedName(DatabaseConstant.BIRTH_DATE) @ColumnInfo(name = DatabaseConstant.BIRTH_DATE) val birthDate: String? = null,
)
