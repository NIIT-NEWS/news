package com.sychen.basic.Utils

import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "TAG"

object Show {
    fun showToastShort(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
    }

    fun showToastLong(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG)
    }

    fun showLog(message: String) {
        Log.e(TAG, message)
    }
}