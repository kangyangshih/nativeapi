// DESC : 提供串接 API 的 API

package com.wolves.anunapidemo

import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.login.widget.LoginButton
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object AndroidAPI {

    // for fackbook 串接做使用
    private fun showAPKHashCode() {
        try {
            val info = this.mContext.packageManager.getPackageInfo(
                "com.wolvesdigital.biga",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    // 使用的活動實體
    lateinit var mContext: AppCompatActivity
    lateinit var mLoginUIPopupWindow: cLoginUIPopupWindow

    // 做初使化的動作
    fun initObject(context: AppCompatActivity) {
        println("~【AndroidAPI】initObject ~")
        // 使用的實體
        this.mContext = context
        // 初使化實體
        BillingSystem.initObject(context)
        //FBSystem.initObject(context)
        // 打開介面
        //mLoginUIPopupWindow = cLoginUIPopupWindow.ConfirmPopupWindowBuilder.init(this.mContext).build()
        showAPKHashCode()
    }

    //--------------------------------------------------
    // 提供呼叫的 API
    //--------------------------------------------------
    //--------------------------------------------------
    // 登入 + 註冊 API
    //--------------------------------------------------
    // 打開登入 / 註冊
    fun startLogin() {
        println("~【AndroidAPI】startLogin ~")
        mLoginUIPopupWindow.show()
    }

    //--------------------------------------------------
    // 儲值功能
    //--------------------------------------------------
    // 設定商品
    fun setSkus(items: Array<String>) {
        BillingSystem.setSkus(items)
    }

    // 給儲值成功呼叫的 callback
    lateinit var mDepositeFinish: (String) -> Unit
    fun depositeMoneyStr(itemStr: String, callback: (String) -> Unit) {
        println("~【AndroidAPI】depositeMoneyStr : $itemStr")
        this.mDepositeFinish = callback
        BillingSystem.googleBillingUtil.purchaseInApp(this.mContext, itemStr)
    }

    //--------------------------------------------------
    // FB 登入
    //--------------------------------------------------
    lateinit var mLoginFBFinish: (String) -> Unit
    fun LoginFB(callback: (String) -> Unit) {
        println("~【AndroidAPI】LoginFB")
        this.mLoginFBFinish = callback
        FBSystem.loginFB()
    }
}
