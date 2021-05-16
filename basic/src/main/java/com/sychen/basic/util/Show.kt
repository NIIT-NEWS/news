package com.sychen.basic.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.MyApplication.Companion._context


object Show {

    @SuppressLint("ShowToast")
    fun showToastShort(message: String) {
        Toast.makeText(_context, message, Toast.LENGTH_SHORT)
    }

    @SuppressLint("ShowToast")
    fun showToastLong(message: String) {
        Toast.makeText(_context, message, Toast.LENGTH_LONG)
    }

    fun showLog(message: String) {
        Log.e(TAG, message)
    }
}