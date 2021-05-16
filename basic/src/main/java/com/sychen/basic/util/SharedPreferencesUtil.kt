
package com.sychen.basic.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sychen.basic.MyApplication.Companion._context
import com.sychen.basic.R
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
object  SharedPreferencesUtil {
    private var mContext: Context = _context!!
    var shp: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        val name = mContext.resources.getString(R.string.MY_DATA)
        shp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE)
        editor = shp.edit()
    }

    /**
     * 写入
     */
    fun saveArrayList(key: String, value: ArrayList<String>) {
        editor.putString(key, Gson().toJson(value))
            .apply()
    }

    inline fun <reified T : Any> sharedPreferencesSave(key: String, value: T) = when (T::class) {
        String::class -> editor.putString(key, value.toString()).apply()
        Int::class -> editor.putInt(key, value.toString().toInt()).apply()
        Boolean::class -> editor.putBoolean(key,value.toString().toBoolean()).apply()
        Float::class -> editor.putFloat(key,value.toString().toFloat()).apply()
        Long::class -> editor.putLong(key,value.toString().toLong()).apply()
        ArrayList<String>()::class -> editor.putString(key, Gson().toJson(value)).apply()
        else -> throw IllegalArgumentException("Type not supported: ${T::class.java}")
    }

    /**
     * 读取
     * @return
     */
    fun loadArrayList(key: String): List<Long> {
        val data = shp.getString(key, "")
        return Gson().fromJson(data, object : TypeToken<List<Long>>() {}.type)
    }

    inline fun <reified T : Any> sharedPreferencesLoad(key: String) = when (T::class) {
        String::class -> shp.getString(key,"")
        Int::class -> shp.getInt(key,0)
        Boolean::class -> shp.getBoolean(key,false)
        Float::class -> shp.getFloat(key,0f)
        Long::class -> shp.getLong(key,0L)
        else -> throw IllegalArgumentException("Type not supported: ${T::class.java}")
    }
}