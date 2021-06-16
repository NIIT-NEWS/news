package com.sychen.user.ui.userset

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.MyApplication.Companion._context
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.dataStoreRead
import com.sychen.basic.util.dataStoreSave
import com.sychen.basic.util.getRealPath
import com.sychen.user.R
import com.sychen.user.network.model.User
import com.sychen.user.network.model.UserInfo
import com.sychen.user.ui.main.UserViewModel
import com.sychen.user.ui.previewcamera.PreviewPhotoViewModel
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.select_avatar.*
import kotlinx.android.synthetic.main.user_set_fragment.*
import kotlinx.coroutines.launch
import java.io.File


class UserSetFragment : Fragment() {
    companion object {
        const val fromAlbum = 2
        const val takePhoto = 1
    }

    private val viewModel by lazy {
        UserSetViewModel()
    }
    private lateinit var previewPhotoViewModel: PreviewPhotoViewModel
    private lateinit var userInfo: UserInfo
    private lateinit var userViewModel: UserViewModel
    private lateinit var imageUri: Uri
    private lateinit var outputImage: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_set_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        previewPhotoViewModel = ViewModelProvider(this).get(PreviewPhotoViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        //获取用户信息
        lifecycleScope.launch {
            userInfo = Gson().fromJson(dataStoreRead<String>("USER_INFO"), UserInfo::class.java)
        }
        initView()
    }

    /**
     * 初始化视图
     * 对用户名进行设置
     * 对用户头像进行设置
     * 对toolbar进行设置
     */
    @SuppressLint("Range")
    private fun initView() {
        user_set_avatar.apply {
            this.load(userInfo.avatar) {
                // 淡入淡出
                crossfade(true)
                transformations(RoundedCornersTransformation(18F))
                placeholder(R.drawable.ic_baseline_photo_24)
                error(R.drawable.ic_baseline_error_24)
            }
            setOnClickListener {
                bottomDialog(requireContext())
            }
        }
        user_set_name.apply {
            this.text = userInfo.nickname
        }
        name_layout.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_userSetFragment_to_setNameFragment)
        }
        user_set_toolbar.apply {
            setNavigationOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_userSetFragment_to_userFragment2)
            }
        }
    }

    /**
     *点击头像进行头像更换
     * 在底部弹出dialog
     */
    private fun bottomDialog(context: Context) {
        //1、使用Dialog、设置style
        Dialog(context, R.style.DialogTheme).apply {
            //2、设置布局
            setContentView(R.layout.select_avatar)
            window!!.apply {
                //设置在底部弹出
                setGravity(Gravity.BOTTOM)
                //设置弹出动画
                setWindowAnimations(R.style.main_menu_animStyle)
                //设置对话框大小
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            show()
            tv_take_photo.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_userSetFragment_to_cameraxFragment)
                this.dismiss()
            }
            tv_take_pic.setOnClickListener {
                //打开文件选择器
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                //指定只显示图片
                intent.type = "image/*"
                startActivityForResult(intent, fromAlbum)
                this.dismiss()
            }
            tv_cancel.setOnClickListener {
                this.dismiss()
            }
        }
    }


    /**
     * 从本地读取到选取的uri
     * 1、更新图片,上传成功会返回服务器的图片网络路径
     * 2、将用户信息转换成json数据
     * 3、更新服务器的用户信息，主要是头像
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        Log.e(TAG, "onActivityResult: uri$uri", )
                        lifecycleScope.launch {
                            //从相册选取图片时获得的uri,保存到本地
                            dataStoreSave("PHOTO_URI", uri.toString())
                        }
                        val realPathFromUri =
                            getRealPath.getRealPathFromUri(requireContext(), uri)
                        val file = File(realPathFromUri)
                        Log.e(TAG, "this is fromAlbum: $file",)
                        previewPhotoViewModel.uploadAvatar(file).observe(requireActivity(), {
                            viewModel.updateAvatar(it.url)
                        })
                        user_set_avatar.apply {
                            this.load(uri) {
                                // 淡入淡出
                                crossfade(true)
                                transformations(RoundedCornersTransformation(18F))
                                placeholder(R.drawable.ic_baseline_photo_24)
                                error(R.drawable.ic_baseline_error_24)
                            }
                        }
                        DialogUtil.alertDialog(requireContext(), "更换成功！")
                    }
                }
            }
        }
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        return when (exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle() //回收
        return rotatedBitmap
    }


}