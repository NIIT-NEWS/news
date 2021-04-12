package com.sychen.login

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.room.Room
import com.sychen.login.database.UserDatabase
import com.sychen.login.database.dao.UserDao
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {
    private lateinit var userDao: UserDao
    private lateinit var db: UserDatabase

    @Before
    fun createDb(){
    }
}