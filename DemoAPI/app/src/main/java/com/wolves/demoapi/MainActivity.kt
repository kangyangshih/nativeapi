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
    fun onLogin(view: View) {
        WolvesAPI.onLogin(this)
    }

    // 【按鈕動作】設定品項
    fun setSkus (view : View)
    {
        // 設定商品
        WolvesAPI.setSkus(
            this,
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

    // 【按鈕動作】 儲值
    fun onDeposite(view: View) {
        // 做購買的動作
        WolvesAPI.depositeMoneyStr(this,"happy_a_33") { purchaseToken ->
            println("[2] 消耗商品成功:$purchaseToken")
        }
    }

}

