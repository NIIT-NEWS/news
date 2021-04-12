package com.sychen.login.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sychen.login.database.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {
    private val readAllData:LiveData<List<User>>
    private val repository:UserRepository

    init {
        val userDao = UserDatabase.getDatabaseInstance(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun insertUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertUser(user)
        }
    }

    fun deleteUserById(id:Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserById(id)
        }
    }

    fun verifyExist(name:String):LiveData<List<User>>{
        return repository.getUserByName(name)
    }
}