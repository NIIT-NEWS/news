package com.sychen.home.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sychen.home.db.dao.NewsDao
import com.sychen.home.db.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    // 通过伴生对象实现单例模式
    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabaseInstance(context: Context): NewsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}