package com.sychen.login.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.sychen.login.database.dao.UserDao
import com.sychen.login.database.model.User
import okhttp3.internal.Internal.instance


@Database(entities = [User::class], version = 1,exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // 通过伴生对象实现单例模式
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabaseInstance(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}