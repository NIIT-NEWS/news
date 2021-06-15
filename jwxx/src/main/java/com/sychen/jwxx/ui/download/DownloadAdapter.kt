package com.sychen.jwxx.ui.download

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.ProgressDialog
import com.sychen.basic.util.Show
import com.sychen.jwxx.R
import com.sychen.jwxx.network.DowloadStatus
import com.sychen.jwxx.network.IDowloadBuild
import com.sychen.jwxx.network.model.PublicData
import kotlinx.android.synthetic.main.download_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class DownloadAdapter :
    ListAdapter<PublicData, DownloadListViewHolder>(DIFFCALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadListViewHolder {
        return DownloadListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.download_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("HandlerLeak", "ResourceAsColor")
    @InternalCoroutinesApi
    override fun onBindViewHolder(holder: DownloadListViewHolder, position: Int) {
        val downloadList = getItem(position)
        with(holder.itemView) {
            download_img.load(R.drawable.ic_download)
            content.text = downloadList.title
            download_img.setOnClickListener {
                Log.e(TAG, "onBindViewHolder: download_img.setOnClickListener")
                GlobalScope.launch {
                    DownloadViewModel().download(downloadList.url, object : IDowloadBuild() {
                        override fun getContext(): Context = context

                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun getDowloadFile(): File? {
                            return File(
                                getContext().getExternalFilesDir(null)?.absolutePath,
                                downloadList.title
                            )
                        }

                        override fun getFileName(): String {
                            return downloadList.title
                        }

                        //android10之后如果下载的文件需要传递给外部app，建议直接下载成uri
                        override fun getUri(contentType: String): Uri? {
                            //下载到共享目录，这里需要考虑android10以上
                            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                val values = ContentValues().apply {
                                    put(
                                        MediaStore.MediaColumns.DISPLAY_NAME,
                                        downloadList.title
                                    ) //文件名
                                    put(MediaStore.MediaColumns.MIME_TYPE, contentType) //文件类型
                                    put(
                                        MediaStore.MediaColumns.RELATIVE_PATH,
                                        Environment.DIRECTORY_DOWNLOADS
                                    )
                                }
                                getContext().contentResolver.insert(
                                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                                    values
                                )
                            } else
                                Uri.fromFile(File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + File.separator + downloadList.title))
                            return uri
                        }
                    }).collect { status ->
                        when (status) {
                            is DowloadStatus.DowloadErron -> {
                                Snackbar.make(holder.itemView, "下载错误！", Snackbar.LENGTH_SHORT)
                                    .setAction("查看") {
                                        Show.showToastLong(status.toString())
                                    }
                                    .show()
                            }
                            is DowloadStatus.DowloadSuccess -> {
                                Show.showLog("下载成功")
                                Thread {
                                    Looper.prepare()
                                    DialogUtil.alertDialog(holder.itemView.context, "下载成功")
                                    download_img.load(R.drawable.ic_download2)
                                    Looper.loop()
                                }.start()

                            }
                            is DowloadStatus.DowloadProcess -> {
                                Show.showLog("进度：${status.process}")
                            }
                        }
                    }
                }
            }

        }
    }

    object DIFFCALLBACK : DiffUtil.ItemCallback<PublicData>() {
        override fun areItemsTheSame(oldItem: PublicData, newItem: PublicData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PublicData,
            newItem: PublicData
        ): Boolean {
            return oldItem == newItem
        }
    }
}

class DownloadListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)