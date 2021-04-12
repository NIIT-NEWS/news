package com.sychen.login.database

import androidx.lifecycle.LiveData
import com.sychen.login.database.dao.UserDao
import com.sychen.login.database.model.User

class UserRepository(private val userdao: UserDao) {

    val readAllData: LiveData<List<User>> = userdao.findAll()

    fun getUserByName(name:String):LiveData<List<User>>{
       return userdao.findUserByUserName(name)
    }

    suspend fun insertUser(user: User) {
        userdao.insert(user)
    }

    suspend fun deleteUserById(id: Long) {
        userdao.deleteUserById(id)
    }
}