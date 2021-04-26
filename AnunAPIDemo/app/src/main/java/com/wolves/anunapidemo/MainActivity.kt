package com.wolves.anunapidemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.BuildConfig
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        println("[system] onCreate------------------")
        super.onCreate(savedInstanceState)
        // [API] 做初使化的動作
        setContentView(R.layout.activity_main)
    }

    // 【範例】 開啟登入/儲值面版 (實作中)
    fun onShow(view: View) {
        // 開啟自動登入的動作
        AndroidAPI.startLogin()
    }

    // 【範例】 初使化API
    fun onInit(view: View) {
        // [API] 做初使化的動作
        AndroidAPI.initObject(this)
        // 設定商品列表 (可以提早做)
        AndroidAPI.setSkus(
            arrayOf(
                "first_happy_33",
                "first_happy_170",
                "happy_a_33",
                "happy_a_170",
                "happy_a_330",
                "happy_a_490",
                "happy_a_990",
                "happy_a_1690",
                "happy_a_3290"
            )
        )
    }

    // 【範例】 儲值
    fun onDepositeMoney(view: View) {
        // 做購買的動作
        AndroidAPI.depositeMoneyStr("happy_a_33") { purchaseToken ->
            println("[2] 消耗商品成功:$purchaseToken")
        }
    }

    // 【範例】 FB
    fun onFB(view: View) {
        println("[onFB]")
        // 做購買的動作
        AndroidAPI.LoginFB() { fbToken ->
            println("[2] onFB:$fbToken")
        }
    }

}
