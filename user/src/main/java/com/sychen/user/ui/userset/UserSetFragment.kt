package com.sychen.user.ui.userset

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.DialogUtil
import com.sychen.basic.util.dataStoreRead
import com.sychen.basic.util.dataStoreSave
import com.sychen.basic.util.getRealPath
import com.sychen.user.R
import com.sychen.user.network.model.UserInfo
import com.sychen.user.ui.main.UserViewModel
import com.sychen.user.ui.previewcamera.PreviewPhotoViewModel
import kotlinx.android.synthetic.main.alert_dialog.*
import kotlinx.android.synthetic.main.select_avatar.*
import kotlinx.android.synthetic.main.user_set_fragment.*
import kotlinx.coroutines.launch
import java.io.*
import kotlin.math.roundToInt


class UserSetFragment : Fragment() {
    companion object {
        const val fromAlbum = 2
    }

    private val viewModel by lazy {
        UserSetViewModel()
    }
    private lateinit var previewPhotoViewModel: PreviewPhotoViewModel
    private lateinit var userInfo: UserInfo
    private lateinit var userViewModel: UserViewModel

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
        //??????????????????
        lifecycleScope.launch {
            userInfo = Gson().fromJson(dataStoreRead<String>("USER_INFO"), UserInfo::class.java)
        }
        initView()
    }

    /**
     * ???????????????
     * ????????????????????????
     * ???????????????????????????
     * ???toolbar????????????
     */
    @SuppressLint("Range")
    private fun initView() {
        user_set_avatar.apply {
            this.load(userInfo.avatar) {
                // ????????????
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
     *??????????????????????????????
     * ???????????????dialog
     */
    private fun bottomDialog(context: Context) {
        //1?????????Dialog?????????style
        Dialog(context, R.style.DialogTheme).apply {
            //2???????????????
            setContentView(R.layout.select_avatar)
            window!!.apply {
                //?????????????????????
                setGravity(Gravity.BOTTOM)
                //??????????????????
                setWindowAnimations(R.style.main_menu_animStyle)
                //?????????????????????
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            show()
            tv_take_photo.setOnClickListener {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_userSetFragment_to_cameraxFragment)
                this.dismiss()
            }
            tv_take_pic.setOnClickListener {
                //?????????????????????
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                //?????????????????????
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
     * ???????????????????????????uri
     * 1???????????????,???????????????????????????????????????????????????
     * 2???????????????????????????json??????
     * 3???????????????????????????????????????????????????
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        Log.e(TAG, "onActivityResult: uri$uri")
                        lifecycleScope.launch {
                            //?????????????????????????????????uri,???????????????
                            dataStoreSave("PHOTO_URI", uri.toString())
                        }
                        var file = uriToFileApiQ(uri, requireContext())
                        Log.e(TAG, "this is fromAlbum: $file")
                        previewPhotoViewModel.uploadAvatar(file!!).observe(requireActivity(), {
                            viewModel.updateAvatar(it.url)
                        })
                        user_set_avatar.apply {
                            this.load(uri) {
                                // ????????????
                                crossfade(true)
                                transformations(RoundedCornersTransformation(18F))
                                placeholder(R.drawable.ic_baseline_photo_24)
                                error(R.drawable.ic_baseline_error_24)
                            }
                        }
                        DialogUtil.alertDialog(requireContext(), "???????????????")
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun uriToFileApiQ(uri: Uri?, context: Context): File? {
        var file: File? = null
        if (uri == null) return file
        //android10????????????
        if (uri.scheme == ContentResolver.SCHEME_FILE) {
            file = File(uri.path)
        } else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //??????????????????????????????
            val contentResolver = context.contentResolver
            val displayName: String =
                (System.currentTimeMillis() + ((Math.random() + 1) * 1000).roundToInt()).toString() + "." + MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(contentResolver.getType(uri))
            try {
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val cache = File(context.cacheDir.absolutePath, displayName)
                val fos = FileOutputStream(cache)
                FileUtils.copy(inputStream!!, fos)
                file = cache
                fos.close()
                inputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

}