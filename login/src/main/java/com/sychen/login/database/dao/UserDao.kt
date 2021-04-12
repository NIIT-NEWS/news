package com.sychen.login.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sychen.login.database.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User): Int

    @Delete
    suspend fun delete(user: User): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun findAll(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun findById(id: Long): User

    @Query("SELECT token FROM user_table WHERE user_name =:username")
    suspend fun getTokenByName(username: String):String

    @Query("SELECT * FROM user_table WHERE user_name = :username")
    fun findUserByUserName(username: String):LiveData<List<User>>

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteUserById(id: Long)
}