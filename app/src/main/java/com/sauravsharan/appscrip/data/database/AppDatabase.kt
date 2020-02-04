package com.sauravsharan.appscrip.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sauravsharan.appscrip.data.database.dao.QuestionsDao
import com.sauravsharan.appscrip.data.database.dao.UserDao
import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.User
import com.sauravsharan.appscrip.data.util.DateConverter
import com.sauravsharan.appscrip.data.util.ListConverter
import com.sauravsharan.appscrip.data.util.MapConverter

@Database(
    entities = [
        Questions::class,
        User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ListConverter::class, MapConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val questionsDao: QuestionsDao
    abstract val userDao: UserDao

    companion object {

        private const val DATABASE_NAME = "com.sauravsharan.appscrip.data.database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }

        }
    }

}