package com.sauravsharan.appscrip.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.sauravsharan.appscrip.data.database.model.User

@Dao
abstract class UserDao: BaseDao<User> {

    @Query("SELECT * FROM user_table")
    abstract suspend fun getAllUsers(): List<User>

    @Delete
    abstract suspend fun deleteUser(user: User)

    @Update
    abstract suspend fun updateUser(user: User)

    @Query("DELETE FROM user_table")
    abstract suspend fun deleteAllUsers()

}