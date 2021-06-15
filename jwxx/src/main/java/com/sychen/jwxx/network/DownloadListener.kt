package com.sychen.jwxx.network

import android.content.Context
import android.net.Uri
import java.io.File

sealed class DowloadStatus {
    class DowloadProcess(val currentLength: Long, val length: Long, val process: Float) :DowloadStatus()
    class DowloadErron(val t: Throwable) : DowloadStatus()
    class DowloadSuccess(val uri: Uri) : DowloadStatus()
}

abstract class IDowloadBuild {
    open fun getFileName(): String? = null
    open fun getUri(contentType: String): Uri? = null
    open fun getDowloadFile(): File? = null
    abstract fun getContext(): Context //贪方便的话，返回Application就行
}

class DowloadBuild(val cxt: Context):IDowloadBuild(){
    override fun getContext(): Context = cxt
}