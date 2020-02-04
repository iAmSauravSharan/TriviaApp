package com.sauravsharan.appscrip.data.database

import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.User

interface DatabaseHelper {

    suspend fun saveQuestion(questions: Questions)

    suspend fun saveUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun getAllQuestions(): List<Questions>

    suspend fun deleteAllQuestions()

    suspend fun getAllUsers(): List<User>

    suspend fun deleteAllUsers()

}