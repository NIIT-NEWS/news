package com.sychen.jwxx.ui.download

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.network.BaseResult
import com.sychen.basic.util.Show
import com.sychen.jwxx.network.DowloadStatus
import com.sychen.jwxx.network.IDowloadBuild
import com.sychen.jwxx.network.JwxxRetrofitDownload
import com.sychen.jwxx.network.JwxxRetrofitUtil
import com.sychen.jwxx.network.model.PublicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DownloadViewModel : ViewModel() {
    private var _downloadList = MutableLiveData<List<PublicData>>()
    val downloadList: LiveData<List<PublicData>> = _downloadList

    init {
        getAllDownload()
    }

    private fun getAllDownload() {
        JwxxRetrofitUtil.api.getAllDownload()
            .enqueue(object : Callback<BaseResult<List<PublicData>>> {
                override fun onResponse(call: Call<BaseResult<List<PublicData>>>, response: Response<BaseResult<List<PublicData>>>) {
                    when (response.body()!!.code) {
                        200 -> {
                            _downloadList.postValue(response.body()!!.data!!)
                            Show.showLog("公告获取成功")
                        }
                        201 -> {
                            Show.showLog("公告获取失败")
                        }
                    }
                }

                override fun onFailure(call: Call<BaseResult<List<PublicData>>>, t: Throwable) {
                    Show.showLog("公告获取失败$t")
                }
            })
    }


    fun download(url: String, build: IDowloadBuild) = flow{
        //UrlService.downloadFile()，这部分不用我教了吧
        val response = JwxxRetrofitDownload.retrofit?.downloadFile(url)
        response?.body()?.let { body ->
            val length = body.contentLength()
            val contentType = body.contentType().toString()
            val ios = body.byteStream()
            val info = try {
                dowloadBuildToOutputStream(build, contentType)
            } catch(e:Exception){
                emit(DowloadStatus.DowloadErron(e))
                DowloadInfo(null)
                return@flow
            }
            val ops = info.ops
            if (ops == null) {
                emit(DowloadStatus.DowloadErron(RuntimeException("下载出错")))
                return@flow
            }
            //下载的长度
            var currentLength: Int = 0
            //写入文件
            val bufferSize = 1024 * 8
            val buffer = ByteArray(bufferSize)
            val bufferedInputStream = BufferedInputStream(ios, bufferSize)
            var readLength: Int = 0
            while (bufferedInputStream.read(buffer, 0, bufferSize)
                    .also { readLength = it } != -1
            ) {
                ops.write(buffer, 0, readLength)
                currentLength += readLength
                emit(
                    DowloadStatus.DowloadProcess(
                        currentLength.toLong(),
                        length,
                        currentLength.toFloat() / length.toFloat()
                    )
                )
            }
            bufferedInputStream.close()
            ops.close()
            ios.close()
            if (info.uri != null)
                emit(DowloadStatus.DowloadSuccess(info.uri))
            else emit(DowloadStatus.DowloadSuccess(Uri.fromFile(info.file)))

        } ?: kotlin.run {
            emit(DowloadStatus.DowloadErron(RuntimeException("下载出错")))
        }
    }.flowOn(Dispatchers.IO)

    private fun dowloadBuildToOutputStream(build: IDowloadBuild, contentType: String): DowloadInfo {
        val context = build.getContext()
        val uri = build.getUri(contentType)
        if (build.getDowloadFile() != null) {
            val file = build.getDowloadFile()!!
            return DowloadInfo(FileOutputStream(file), file)
        } else if (uri != null) {
            return DowloadInfo(context.contentResolver.openOutputStream(uri), uri = uri)
        } else {
            val name = build.getFileName()
            val fileName = if(!name.isNullOrBlank()) name else "${System.currentTimeMillis()}.${
                MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentType)
            }"
            val file = File("${context.filesDir}",fileName)
            return DowloadInfo(FileOutputStream(file), file)
        }
    }

    private class DowloadInfo(val ops: OutputStream?, val file: File? = null, val uri: Uri? = null)
}