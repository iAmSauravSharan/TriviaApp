package com.sauravsharan.appscrip.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "question_table")
data class Questions(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    val questionId: Long = 0,

    @ColumnInfo(name = "question")
    @SerializedName("question")
    val questionText: String,

    @ColumnInfo(name = "options")
    @SerializedName("options")
    val options: List<String>,

    @ColumnInfo(name = "is_multiple_selection_allowed")
    @SerializedName("is_multiple_selection_allowed")
    val isMultipleSelectionAllowed: Boolean

)