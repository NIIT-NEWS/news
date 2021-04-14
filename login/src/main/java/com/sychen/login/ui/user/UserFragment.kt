package com.sychen.login.ui.user

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sychen.basic.dataStoreRead
import com.sychen.login.R
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.user_fragment.*
import kotlinx.coroutines.launch


class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
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
        /**
         * 读取USER_ID
         */
        lifecycleScope.launch {
            val userId = dataStoreRead<String>("USER_ID")
            viewModel.getUserInfo(userId)
        }
        initView()
    }

    //初始化视图
    @SuppressLint("SetTextI18n")
    private fun initView() {
        viewModel.userInfo.observe(requireActivity(), { userInfo ->
            //设置头像
            user_avatar.apply {
                this.load(userInfo.data.avatar) {
                    // 淡入淡出
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_baseline_photo_24)
                    error(R.drawable.ic_baseline_error_24)
                }
            }
            //设置用户名
            user_username.apply {
                this.text = userInfo.data.username
            }
            //
            user_id.apply {
                this.text = "用户ID : "+userInfo.data.id.toString()
            }
            //设置头像背景
            Glide.with(requireContext())
                .asBitmap()
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
                .load(userInfo.data.avatar)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val drawable: Drawable = BitmapDrawable(resources, resource)
                        user_background.background = drawable
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

        })
    }

}