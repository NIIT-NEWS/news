package com.sychen.user.ui.previewcamera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sychen.basic.util.Show
import com.sychen.user.network.UserRetrofitUtil
import com.sychen.user.network.model.UpdateUser
import com.sychen.user.network.model.UploadAvatar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PreviewPhotoViewModel : ViewModel() {
    private var _userAvatarUrl = MutableLiveData<String>()
    val userAvatarUrl: LiveData<String> = _userAvatarUrl

    /**
     * 上传用户头像
     */
    fun uploadAvatar(file: File) {
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
        //上传图片获取url
        UserRetrofitUtil.api.uploadAvatar(description, body)
            .enqueue(object : Callback<UploadAvatar> {
                override fun onResponse(
                    call: Call<UploadAvatar>,
                    response: Response<UploadAvatar>
                ) {
                    if (response.isSuccessful) {
                        when (response.body()!!.code) {
                            200 -> {
                                Show.showLog("用户头像上传成功！${response.body()!!.data.url}")
                                _userAvatarUrl.postValue(response.body()!!.data.url)
                            }
                            else -> Show.showLog("用户头像上传失败")
                        }
                    }
                }

                override fun onFailure(call: Call<UploadAvatar>, t: Throwable) {
                    Show.showLog("用户头像上传失败！${t.message}")
                }
            })
    }


    /**
     * 更新用户信息
     */
    fun updateUserInfo(userInfo: String) {
        val body: RequestBody =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userInfo)
        UserRetrofitUtil.api.updateUserInfo(body)
            .enqueue(object : Callback<UpdateUser> {
                override fun onResponse(
                    call: Call<UpdateUser>,
                    response: Response<UpdateUser>
                ) {
                    if (response.isSuccessful) {
                        when(response.body()!!.code){
                            200-> Show.showLog("用户信息更新成功！${response.body()!!.code}")
                            else-> Show.showLog("用户信息更新失败!${response.body()!!.code}")
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateUser>, t: Throwable) {
                    Show.showLog("用户信息更新失败！${t.message}")
                }
            })
    }
}