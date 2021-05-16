package com.sychen.user.ui.previewcamera

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import coil.load
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.dataStoreRead
import com.sychen.basic.util.dataStoreSave
import com.sychen.user.R
import com.sychen.user.network.model.User
import com.sychen.user.network.model.UserInfo
import com.sychen.user.ui.main.UserViewModel
import kotlinx.android.synthetic.main.preview_photo_fragment.*
import kotlinx.coroutines.launch
import java.io.File


class PreviewPhotoFragment : Fragment() {

    private lateinit var previewPhotoViewModel: PreviewPhotoViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var userInfo: UserInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.preview_photo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        previewPhotoViewModel = ViewModelProvider(this).get(PreviewPhotoViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        //读取用户信息
        lifecycleScope.launch {
            userInfo = Gson().fromJson(
                dataStoreRead<String>("USER_INFO"), UserInfo::class.java
            )
        }
        //获取刚刚拍照的Uri路径并在preview_photo上显示、预览
        val photoUri = arguments?.getString("PHOTO_URI").toString()
        preview_photo.load("file://$photoUri") {
            crossfade(true) // 淡入淡出
            placeholder(R.drawable.ic_baseline_photo_24)
            error(R.drawable.ic_baseline_error_24)
        }
        ph_back.apply {
            setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_previewPhotoFragment_to_cameraxFragment)
            }
        }
        //用户确定按钮
        //提交信息1、上传头像获取url地址
        //2、更新用户信息
        ph_confrim_btn.apply {
            setOnClickListener {
                val file = File(photoUri)
                Log.e(TAG, "onActivityCreated: $file" )
                previewPhotoViewModel.uploadAvatar(file)
                userGson()
                saveUserInfo()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_previewPhotoFragment_to_userSetFragment)
            }
        }
    }

    /**
     * 将用户的信息转换成json数据
     * 通过view model发送到服务器
     */
    private fun userGson() {
        previewPhotoViewModel.userAvatarUrl.observe(requireActivity(), {
            Log.e(TAG, "userGson: $it")
            val gson = GsonBuilder().create()
            val updateUser = User()
            with(updateUser) {
                userid = userInfo.data.id
                username = userInfo.data.username
                password = userInfo.data.password
                avatar = it.toString()
                role = userInfo.data.role
            }
            val user = gson.toJson(updateUser)
            previewPhotoViewModel.updateUserInfo(user)
        })
    }

    /**
     * 存储用户信息
     * 在此处主要更新头像信息
     */
    private fun saveUserInfo() {
        lifecycleScope.launch {
            userViewModel.getUserInfo(dataStoreRead("USER_ID"))
            userViewModel.userInfo.observe(requireActivity(), { userInfo ->
                lifecycleScope.launch {
                    dataStoreSave<String>("USER_INFO", Gson().toJson(userInfo))
                }
            })
        }
    }

    /**
     * 把content uri转为 文件路径
     *
     * @param contentUri      要转换的content uri
     * @param contentResolver 解析器
     * @return
     */
    fun getFilePathFromContentUri(
        contentUri: Uri,
        contentResolver: ContentResolver
    ): String? {
        val filePath: String
        val filePathColumn = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor: Cursor = contentResolver.query(contentUri, filePathColumn, null, null, null)!!
        cursor.moveToFirst()
        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
        filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }

}