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
import com.sychen.basic.util.getRealPath
import com.sychen.user.R
import com.sychen.user.network.model.User
import com.sychen.user.network.model.UserInfo
import com.sychen.user.ui.main.UserViewModel
import com.sychen.user.ui.userset.UserSetViewModel
import kotlinx.android.synthetic.main.preview_photo_fragment.*
import kotlinx.coroutines.launch
import java.io.File


class PreviewPhotoFragment : Fragment() {

    private lateinit var previewPhotoViewModel: PreviewPhotoViewModel
    private lateinit var userViewModel: UserViewModel
    private val viewModel by lazy {
        UserSetViewModel()
    }
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
//                val file = File()
//                previewPhotoViewModel.uploadAvatar().observe(requireActivity(),{
//                    viewModel.updateAvatar(it.url)
//                })
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_previewPhotoFragment_to_userSetFragment)
            }
        }
    }
}