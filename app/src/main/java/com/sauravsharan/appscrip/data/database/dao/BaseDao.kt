package com.sauravsharan.appscrip.data.database.dao

import androidx.room.Insert

interface BaseDao<T> {

    @Insert
    fun insert(vararg objects: T)

}