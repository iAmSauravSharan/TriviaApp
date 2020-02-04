package com.sauravsharan.appscrip.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import com.sauravsharan.appscrip.data.util.DateConverter
import java.util.*
import kotlin.collections.HashMap

@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Long = 0L,

    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    val userName: String,

    @ColumnInfo(name = "attempted_answers")
    @SerializedName("attempted_answers")
    val attemptedAnswers: HashMap<Long, String>? = null,

    @ColumnInfo(name = "attempted_at")
    @SerializedName("attempted_at")
    val attemptedAt: Date? = null

)