package com.sauravsharan.appscrip.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.sauravsharan.appscrip.data.database.model.User

@Dao
abstract class UserDao: BaseDao<User> {

    @Query("SELECT * FROM user_table")
    abstract suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM user_table")
    abstract suspend fun deleteAllUsers()

}