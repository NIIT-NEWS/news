package com.sychen.basic.util

import android.content.Context
import android.content.res.Resources
import com.sychen.basic.MyApplication.Companion._context

object StatusBarHeight {
    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    fun get(): Int {
        val resources: Resources = _context!!.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}