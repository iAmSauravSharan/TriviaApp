package com.sauravsharan.appscrip.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.sauravsharan.appscrip.data.database.model.Questions

@Dao
abstract class QuestionsDao: BaseDao<Questions> {

    @Query("SELECT * FROM question_table")
    abstract suspend fun getAllQuestions(): List<Questions>

    @Query("DELETE FROM question_table")
    abstract suspend fun deleteAllQuestions()

}