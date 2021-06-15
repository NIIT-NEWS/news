package com.sychen.home.ui.newdetails

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.MyApplication.Companion.TAG
import com.sychen.basic.util.dataStoreRead
import com.sychen.home.R
import com.sychen.home.network.model.New
import kotlinx.android.synthetic.main.dialog_input_edit.*
import kotlinx.android.synthetic.main.new_details_fragment.*
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class NewDetailsFragment : Fragment() {

    companion object

    fun getInstance(new: New.Data): Fragment {
        val newDetailsFragment = NewDetailsFragment()
        val bundle = Bundle()
        bundle.putString("NEW_INFO", Gson().toJson(new, New.Data::class.java))
        newDetailsFragment.arguments = bundle
        return newDetailsFragment
    }

    private lateinit var viewModel: NewDetailsViewModel
    private lateinit var news: New.Data
    private lateinit var mInputEditDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewDetailsViewModel::class.java)
        try {
            news = Gson().fromJson(arguments?.getString("NEW_INFO"), New.Data::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "NewDetailsFragment-onActivityCreated: ${e.message}")
        }
        initView()
    }


    private fun initView() {
        initInputMsgDialog()
//        new_author.text = news.author
        new_detail_img.load(news.newTitleImgUrl)
        toolbar.apply {
            title = news.newTitle
            setNavigationOnClickListener {
                ARouter.getInstance().build(ARouterUtil.START_MAIN_ACTIVITY).navigation()
            }
        }
        setWebView()
        fab.apply {
            setOnClickListener {
                mInputEditDialog.show()
            }
            setOnFocusChangeListener { v, hasFocus ->
                mInputEditDialog.show()
                val vibrator = (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
                val patter = longArrayOf(1000, 1000, 2000, 50)
                val amplitudes = intArrayOf(120, 130, 150, 170)
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        patter,
                        amplitudes,
                        1
                    )
                )
            }
            setOnLongClickListener {
                love.visibility = View.VISIBLE
                return@setOnLongClickListener true
            }
        }
    }

    /**
     * 发消息弹出框
     */
    private fun initInputMsgDialog() {
        mInputEditDialog = InputEditDialog(requireContext(), R.style.DialogTheme, "写评论")
        mInputEditDialog.apply {
            setCancelable(true)
            window!!.apply {
                attributes.width = requireActivity().windowManager.defaultDisplay.width
                setGravity(Gravity.BOTTOM)
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            }
            confirm_btn.setOnClickListener {
                viewModel.uploadReview(getUserReviewJson())
                viewModel.status.observe(requireActivity(), {
                    when (it) {
                        200 -> Snackbar.make(requireView(), "评论成功", Snackbar.LENGTH_SHORT)
                            .setAction("撤回") {
                                Toast.makeText(requireContext(), "toast", Toast.LENGTH_SHORT).show()
                            }.show()
                        201 -> Snackbar.make(requireView(), "评论失败", Snackbar.LENGTH_SHORT).show()
                    }
                })
                mInputEditDialog.dismiss()
            }
        }
    }

    private fun getUserReviewJson(): String {
        val gson = GsonBuilder().create()
        var reviewJson = ReviewJson()
        with(reviewJson) {
            newId = news.id
            lifecycleScope.launch {
                userId = dataStoreRead<String>("USER_ID").toInt()
            }
            content = mInputEditDialog.et_input_message.text.toString()
            date = getNowDate()
        }
        return gson.toJson(reviewJson)
    }

    private fun getNowDate(): String {
        val localDate = LocalDate.now()
        val year = localDate.year
        val month = localDate.monthValue
        val day = localDate.dayOfMonth
        return "$year-$month-$day"
    }

    @SuppressLint("SetJavaScriptEnabled")
    var setWebView = {
        webview.apply {
            //开启kotlin与H5通信
            settings.javaScriptEnabled = true
            webViewClient = MyWebView()
            webChromeClient = MyWebViewSec()
            //H5与kotlin 通信方式
            //1.h5调用kotlin
            //设置通信桥梁类
            addJavascriptInterface(JavaScriptMe(requireContext(), this), "test")
            loadUrl(news.webUrl)
        }
    }

    private inner class MyWebView : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //kotlin调用js
            //JS定义String变量的时候用单引号，而JAVA是使用双引号。
            val json = "kotlin调用js"
            try {
                webview.loadUrl("javascript:showMessage('$json')")
            } catch (e: Exception) {
                Log.e(TAG, "onPageFinished: ${e.message}")
            }
        }
    }

    private class MyWebViewSec : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onResume() {
        super.onResume()
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (keyEvent.action === KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                Toast.makeText(activity, "按了返回键", Toast.LENGTH_SHORT).show()
                //使用eventbus发送广播启动activity
                val msg = MessageEvent(MessageType.TypeOne).put("startHomeActivity")
                EventBus.getDefault().post(msg)
                requireActivity().finish()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onStop() {
        super.onStop()
        mInputEditDialog.dismiss()
    }

    data class ReviewJson(
        var newId: Int = 0,
        var userId: Int = 0,
        var content: String = "",
        var date: String = ""
    )

}

class JavaScriptMe {

    private var mContext: Context? = null

    private var mWebView: WebView? = null

    constructor(mContext: Context?, mWebView: WebView?) {
        this.mContext = mContext
        this.mWebView = mWebView
    }

    @JavascriptInterface
    fun showToast(json: String) {
        mContext?.let {
        }
    }

    //callback （js嗲用kotlin）
    @JavascriptInterface
    fun getHotelData(method: String) {
        var d = "获取酒店的数据"
        println(d)
//        callback 回传数据
        mContext?.let {
            it.runCatching {
                mWebView?.let {
                    it.loadUrl("javascript:$method('$d')")
                }
            }
        }
    }
}