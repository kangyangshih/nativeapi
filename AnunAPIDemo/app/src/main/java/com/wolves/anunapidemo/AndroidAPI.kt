// DESC : 提供串接 API 的 API

package com.wolves.anunapidemo

import androidx.appcompat.app.AppCompatActivity

object AndroidAPI {

    // 使用的活動實體
    lateinit var mContext: AppCompatActivity
    lateinit var mLoginUIPopupWindow : cLoginUIPopupWindow

    // 做初使化的動作
    fun initObject (context : AppCompatActivity){
        println ("~【AndroidAPI】initObject ~")
        // 使用的實體
        this.mContext = context
        // 初使化實體
        BillingSystem.initObject(context)
        // 打開介面
        //mLoginUIPopupWindow = cLoginUIPopupWindow.ConfirmPopupWindowBuilder.init(this.mContext).build()
    }

    //--------------------------------------------------
    // 提供呼叫的 API
    //--------------------------------------------------
    //--------------------------------------------------
    // 登入 + 註冊 API
    //--------------------------------------------------
    // 打開登入 / 註冊
    fun startLogin (){
        println ("~【AndroidAPI】startLogin ~")
        mLoginUIPopupWindow.show()
    }

    //--------------------------------------------------
    // 儲值功能
    //--------------------------------------------------
    // 給儲值成功呼叫的 callback
    lateinit var mDepositeFinish: (String) -> Unit
    fun depositeMoneyStr (itemStr : String, callback: (String)->Unit) {
        println ("~【AndroidAPI】depositeMoneyStr : $itemStr")
        this.mDepositeFinish = callback
        BillingSystem.googleBillingUtil.purchaseInApp(this.mContext, itemStr)
    }

    //--------------------------------------------------
    // FB 登入
    //--------------------------------------------------

}
