package com.sychen.basic.Utils

import android.content.Context
import android.content.res.Resources

class StatusBarHeight {
    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    fun get(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}