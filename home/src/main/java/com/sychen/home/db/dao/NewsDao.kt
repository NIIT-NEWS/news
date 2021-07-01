package com.sychen.home.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sychen.home.db.model.News

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: News)

    @Update
    suspend fun update(news: News): Int

    @Delete
    suspend fun delete(news: News): Int

    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun findAll(): LiveData<List<News>>
}