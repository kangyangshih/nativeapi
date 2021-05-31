package com.wolves.demoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.wolves.wolveslib.AndroidAPI
import com.wolves.wolveslib.WolvesAPI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 【按鈕動作】 做初使化的動作
    fun onInit(view: View) {
        WolvesAPI.onInit(this)
    }

    // 【按鈕動作】 做初使化的動作
    fun onLogin(view: View) {
        WolvesAPI.onLogin(this)
    }

    // 【按鈕動作】 儲值
    fun onDeposite(view: View) {
        WolvesAPI.onDeposite ()
    }

    // 【按鈕動作】 FB登入
    fun onFB(view: View) {
        WolvesAPI.onFB ()
    }

}

