// DESC : 主要開放給外部做使用的介面
// Author : nick.shih
package com.wolves.wolveslib

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

object WolvesAPI {

    // 開啟登入面版
    fun onLogin(context: AppCompatActivity) {
        AndroidAPI.showMsg("[WolvesAPI] onLogin")
        // 轉到 login manager
        LoginMgr.onLogin(context)
    }

    //--------------------------------------------------
    // 儲值功能
    //--------------------------------------------------
    // 設定商品
    fun setSkus(context: AppCompatActivity, items: Array<String>) {
        BillMgr.setSkus(context, items)
    }

    // 給儲值成功呼叫的 callback
    lateinit var mDepositeFinish: (String) -> Unit
    fun depositeMoneyStr(context: AppCompatActivity, itemStr: String, callback: (String) -> Unit) {
        println("~【AndroidAPI】depositeMoneyStr : $itemStr")
        this.mDepositeFinish = callback
        BillMgr.googleBillingUtil.purchaseInApp(context, itemStr)
    }

    // 使用帳號密碼登入
    fun loginAP (account:String, password:String, callback: (String) -> Unit) {
        WebAPI.login(account, password, callback)
    }

    // 取得用戶資料
    fun playInfo (token:String, callback: (JSONObject) -> Unit) {
        WebAPI.userInfo(token, callback)
    }

    //-----------------------------------------------------------------
    // 從 web 取得 webID
    @JavascriptInterface
    fun FBIDFeedback (key:String)
    {
        println ("[WolvesAPI] FBIDFeedback, key:$key")
        mContext?.runOnUiThread {
            this.mRootView?.removeView(this.mWebViewView)
            mCallback(key)
            mRootView = null
            mWebViewView = null
        }
    }

    var mRootView : ViewGroup? = null
    var mWebViewView : View? = null
    var mContext: AppCompatActivity? = null
    lateinit var mCallback: (String) -> Unit

    @SuppressLint("JavascriptInterface")
    fun getWebFBID (context: AppCompatActivity, rootView:ViewGroup, callback: (String) -> Unit) {
        println ("[WolvesAPI] getWebFBID")
        // 開啟 webview
        //val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        //val inflater: LayoutInflater = context.getLayoutInflater()
        //val layout: View = inflater.inflate(com.wolves.wolveslib.R.layout.web_fb, null)
        //builder.setView(layout)
        //val dialog = builder.create()

        // 取得主介面的 float
        val flater = LayoutInflater.from(context)
        // 把介面動態塞進去
        val view: View = flater.inflate(com.wolves.wolveslib.R.layout.web_fb, null)
        rootView.addView(view)
        mRootView = rootView
        mWebViewView = view
        mContext = context
        mCallback = callback

        // 設定 webView
        //----------------------------------------------
        // 產生 webview
        //----------------------------------------------
        // 取得 view 上的名稱
        val webview = view.findViewById(R.id.webview) as WebView
        //webview.clearCache(true)
        val webSettings = webview.settings
        // 打開  webview javascript 的功能
        webSettings.javaScriptEnabled = true
        // 設定可以儲存的動作
        webSettings.setDomStorageEnabled(true)
        // 設定畫面
        //context.setContentView(webview)
        // 產生 webview
        webview.webViewClient = WebViewClient()
        // 注入給 webview 使用的 kotlin function
        webview.addJavascriptInterface (this, "wd")
        // 載入想開的網址
        webview.loadUrl("https://front.fishrush.com.tw/website/info")
    }
}
