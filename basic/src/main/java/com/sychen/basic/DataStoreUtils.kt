package com.sychen.basic

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object  DataStoreUtils {
    private val data = preferencesKey<String>("data")
    private val name = "token"
    private var dataStore:DataStore<Preferences>?=null
    /**
     * 写入数据
     */
    suspend fun  writeDataToDataStore(context:Context,msg: String){
        if(dataStore==null) {
            dataStore = context.createDataStore(name = name)
        }
        dataStore!!.edit {
                user->
            user[data] = msg
        }
    }
    /**
     * 读取数据
     */
    fun  readDataToDataStore(context:Context): Flow<String> {
        if(dataStore==null) {
            dataStore = context.createDataStore(name = name)
        }
        return dataStore!!.data.map { preferences: Preferences -> preferences[data] ?: "" }
    }
}