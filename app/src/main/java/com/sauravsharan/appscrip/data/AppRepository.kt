package com.sauravsharan.appscrip.data

import android.content.Context
import com.sauravsharan.appscrip.data.database.AppDatabase
import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.User
import com.sauravsharan.appscrip.data.preference.AppPreference

class AppRepository(context: Context): RepositoryHelper {

    private val database: AppDatabase = AppDatabase.getInstance(context)
    private val preference: AppPreference = AppPreference(context)

    override suspend fun saveQuestion(questions: Questions) {
        database.questionsDao.insert(questions)
    }

    override suspend fun saveUser(user: User) {
        database.userDao.insert(user)
    }

    override suspend fun updateUser(user: User) {
        database.userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        database.userDao.deleteUser(user)
    }

    override suspend fun getAllQuestions(): List<Questions> {
        return database.questionsDao.getAllQuestions()
    }

    override suspend fun deleteAllQuestions() {
        database.questionsDao.deleteAllQuestions()
    }


    override suspend fun getAllUsers(): List<User> {
        return database.userDao.getAllUsers()
    }

    override suspend fun deleteAllUsers() {
        database.userDao.deleteAllUsers()
    }


    override fun isAppFirstTimeLaunched(): Boolean {
        return preference.isAppFirstTimeLaunched()
    }

    override fun isAppFirstTimeLaunched(isFirstTimeLaunched: Boolean) {
        preference.isAppFirstTimeLaunched(isFirstTimeLaunched)
    }

    override fun resetPreferences() {
        preference.resetPreferences()
    }

}