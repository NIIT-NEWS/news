package com.sychen.jwxx.ui.content

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.Navigation
import com.sychen.basic.MessageEvent
import com.sychen.basic.MessageType
import com.sychen.basic.MyApplication
import com.sychen.jwxx.R
import kotlinx.android.synthetic.main.fragment_content.*
import org.greenrobot.eventbus.EventBus


class ContentFragment : Fragment() {
    override fun onStart() {
        super.onStart()
        val msg = MessageEvent(MessageType.TypeTwo).put("onStop")
        EventBus.getDefault().post(msg)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.jwxxFragment)
        }
        title.text = arguments?.get("NOTICE_TITLE").toString()
        setWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    var setWebView = {
        webView.apply {
            //开启kotlin与H5通信
            settings.javaScriptEnabled = true
            webViewClient = MyWebView()
            webChromeClient = MyWebViewSec()
            loadUrl(arguments?.getString("NOTICE_URL").toString())
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

    override fun onStop() {
        super.onStop()
        val msg = MessageEvent(MessageType.TypeTwo).put("onStart")
        EventBus.getDefault().post(msg)
    }
}