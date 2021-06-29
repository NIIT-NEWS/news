package com.sychen.jwxx.ui.content

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.Navigation
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.sychen.basic.ARouterUtil
import com.sychen.basic.MyApplication
import com.sychen.basic.activity.BaseActivity
import com.sychen.basic.util.StatusBarHeight
import com.sychen.jwxx.R
import com.sychen.jwxx.network.model.PublicData
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.fragment_content.*


class ContentActivity : BaseActivity() {
    private lateinit var publicData: PublicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        initViews()
    }

    private fun initViews() {
        publicData = Gson().fromJson(intent.getStringExtra("NOTICE_INFO"), PublicData::class.java)
        content_guideline.setGuidelineBegin(StatusBarHeight.get())
        content_toolbar.apply {
            title = publicData.title
            setNavigationOnClickListener {
                ARouter.getInstance().build(ARouterUtil.START_MAIN_ACTIVITY)
                    .navigation()
                finish()
            }
        }
        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    var setWebView = {
        content_webview.apply {
            //开启kotlin与H5通信
            settings.javaScriptEnabled = true
            webViewClient = MyWebView()
            webChromeClient = MyWebViewSec()
            loadUrl(publicData.url)
        }
    }

    private inner class MyWebView : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            val json = "kotlin调用js"
            try {
                webView.loadUrl("javascript:showMessage('$json')")
            } catch (e: Exception) {
                Log.e(MyApplication.TAG, "onPageFinished: ${e.message}")
            }
        }
    }

    private class MyWebViewSec : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }
}