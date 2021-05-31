// DESC : 主要開放給外部做使用的介面
// Author : nick.shih
package com.wolves.wolveslib

import androidx.appcompat.app.AppCompatActivity

object WolvesAPI {
    // 做初使化的動作
    fun onInit(context: AppCompatActivity) {
        // 做顯示的動作
        AndroidAPI.showMsg("[WolvesAPI] onInit")
    }

    // 開啟登入面版
    fun onLogin(context: AppCompatActivity) {
        AndroidAPI.showMsg("[WolvesAPI] onLogin")
        // 轉到 login manager
        LoginMgr.onLogin(context)
    }

    // 實作儲值的動作
    fun onDeposite() {
        AndroidAPI.showMsg("[WolvesAPI] onDeposite")
    }

    // 實作登入FB的動作
    fun onFB() {
        AndroidAPI.showMsg("[WolvesAPI] onFB")
    }
}
