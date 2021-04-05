package com.sychen.basic.Utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sychen.basic.R
import kotlin.collections.ArrayList

class SharedPreferencesUtil(private val context: Context) {
    var userLikesMusicIdList = ArrayList<String>()
    var uid: String = ""

    /**
     * 写入
     */
    fun saveUserLikeMusicIdList() {
        val name = context.resources.getString(R.string.MY_DATA)
        val shp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val editor = shp.edit()
        val key = context.resources.getString(R.string.likesonglist)
        editor.putString(key, Gson().toJson(userLikesMusicIdList))
        editor.apply()
    }
    fun saveUid() {
        val name = context.resources.getString(R.string.MY_DATA)
        val shp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val editor = shp.edit()
        val key = context.resources.getString(R.string.uid)
        editor.putString(key, uid)
        editor.apply()
    }

    /**
     * 读取
     * @return
     */
    fun loadUserLikeMusicIdList(): List<Long> {
        val name = context.resources.getString(R.string.MY_DATA)
        val shp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val key = context.resources.getString(R.string.likesonglist)
        val data = shp.getString(key, "")
        return Gson().fromJson(data, object : TypeToken<List<Long>>() {}.type)
    }
    fun loadUid() : String {
        val name = context.resources.getString(R.string.MY_DATA)
        val shp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val key = context.resources.getString(R.string.uid)
        val data = shp.getString(key, "")
        uid= data.toString()
        return uid
    }
}