package com.wolves.anunapidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 【範例】 初使化API
    fun onInit (view : View) {
        // [API] 做初使化的動作
        AndroidAPI.initObject(this)
    }

    // 【範例】 開啟登入/儲值面版 (實作中)
    fun onShow (view : View) {
        // 開啟自動登入的動作
        AndroidAPI.startLogin()
    }

    // 【範例】 儲值
    fun onDepositeMoney (view:View)
    {
        AndroidAPI.depositeMoneyStr ("happy_a_33") { purchaseToken ->
            println("[2] 消耗商品成功:$purchaseToken")
        }
    }

    // 【範例】 FB
    fun onFB (view : View) {
        println ("[onFB]")
    }

}
