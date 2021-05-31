// DESC : 主要開放給外部做使用的介面
// Author : nick.shih
package com.wolves.wolveslib

import androidx.appcompat.app.AppCompatActivity

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
}
