package com.sychen.user.ui.previewcamera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.Show
import com.sychen.user.network.UserRetrofitUtil
import com.sychen.user.network.model.UpdateUser
import com.sychen.user.network.model.UploadAvatar
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PreviewPhotoViewModel : ViewModel() {
    private val _userAvatarUrl by lazy {
        MutableLiveData<UploadAvatar>()
    }

    /**
     * 上传用户头像
     */
    fun uploadAvatar(file: File) : LiveData<UploadAvatar>{
        // 创建 RequestBody，用于封装构建RequestBody
        val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
        // MultipartBody.Part  和后端约定 好Key，这里的partName是用file
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        // 添加描述
        val descriptionString = "hello, 这是文件描述"
        val description = RequestBody.create(
            MediaType.parse("multipart/form-data"),
            descriptionString
        )
        viewModelScope.launch {
            try {
                val result = UserRetrofitUtil.api.uploadAvatar(description, body)
                Log.e(TAG, "uploadAvatar: $result")
                _userAvatarUrl.postValue(result.data)
            } catch (e: Exception) {
                Log.e(TAG, "uploadAvatar: $e", )
                _userAvatarUrl.postValue(null)
            }
        }
        return _userAvatarUrl
    }
}