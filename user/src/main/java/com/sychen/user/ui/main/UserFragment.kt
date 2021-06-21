package com.sychen.user.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import coil.load
import coil.transform.CircleCropTransformation
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.PermissionUtil
import com.sychen.basic.util.Show
import com.sychen.basic.util.dataStoreSave
import com.sychen.user.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.user_fragment.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.io.File


class UserFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        val msg = MessageEvent(MessageType.TypeTwo).put("onStart")
        EventBus.getDefault().post(msg)
    }

    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        initView()
    }

    //初始化视图
    @SuppressLint("SetTextI18n")
    private fun initView() {
        viewModel.getUserInfo().observe(requireActivity(), { userInfo ->
            //保存用户信息
            lifecycleScope.launch {
                dataStoreSave("USER_INFO", Gson().toJson(userInfo))
            }
            //设置头像
            try {
                user_avatar.load(userInfo.avatar) {
                    // 淡入淡出
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_baseline_photo_24)
                    error(R.drawable.ic_baseline_error_24)
                }
            } catch (e: Exception) {
                Log.e(TAG, "initView: $e")
            }
            //设置用户名
            user_username.text = userInfo.nickname
            //
            user_id.text = "用户账号 : " + userInfo.username
            user_background.let {
                //设置头像背景
                Glide.with(requireContext())
                    .asBitmap()
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
                    .load(userInfo.avatar)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val drawable: Drawable = BitmapDrawable(resources, resource)
                            it.background = drawable
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
                //设置用户信息
                it.setOnClickListener {
                    //进入用户信息页面
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_userFragment2_to_userSetFragment)
                }
            }
        })
        downloadLayout.setOnClickListener { v ->
            var intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "file/*"
            context?.startActivity(intent)
            PermissionUtil.runtimePermission("Manifest.permission.READ_EXTERNAL_STORAGE")
        }
        sitLayout.setOnClickListener { v ->
            val makeScaleUpAnimation =
                ActivityOptionsCompat.makeScaleUpAnimation(v, v.width, v.height, 0, 0)
            ARouter.getInstance()
                .build(ARouterUtil.START_SETTING_ACTIVITY)
                .withOptionsCompat(makeScaleUpAnimation)
                .navigation()
        }
        favorLayout.setOnClickListener { v->
            val makeScaleUpAnimation =
                ActivityOptionsCompat.makeScaleUpAnimation(v, v.width, v.height, 0, 0)
            ARouter.getInstance().build(ARouterUtil.START_COLLECT_ACTIVITY)
                .withOptionsCompat(makeScaleUpAnimation)
                .navigation()
        }
    }
    @RequiresApi(Build.VERSION_CODES.R)
    fun readPicture(fileName:String){
        val path:File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        if (!path.exists()){
            Show.showToastShort("文件路径不存在")
            return
        }
        val file:File = File(path,"*")
        if (!file.exists()){
            Show.showToastShort("文件路径不存在")
            return
        }

    }
    override fun onStop() {
        super.onStop()
        val msg = MessageEvent(MessageType.TypeTwo).put("onStop")
        EventBus.getDefault().post(msg)
    }

}