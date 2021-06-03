package com.wolves.demoapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.wolves.wolveslib.AndroidAPI
import com.wolves.wolveslib.WebAPI
import com.wolves.wolveslib.WolvesAPI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 設定帳號密碼登入
        this.findViewById<EditText>(R.id.et_account).setText("g658118095")
        this.findViewById<EditText>(R.id.et_password).setText("mwmCePfZklIf9spck4eY")
    }

    // 【按鈕動作】 做初使化的動作
    fun onLogin(view: View) {
        WolvesAPI.onLogin(this)
    }

    // 【按鈕動作】設定品項
    fun setSkus(view: View) {
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
        WolvesAPI.depositeMoneyStr(this, "happy_a_33") { purchaseToken ->
            println("[2] 消耗商品成功:$purchaseToken")
        }
    }

    fun toLog(msg: String) {
        println(msg)
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }
    }

    // 【按鈕動作】
    var loginToken: String = ""
    fun onAP(view: View) {
        var account: String = this.findViewById<EditText>(R.id.et_account).text.toString()
        var password: String = this.findViewById<EditText>(R.id.et_password).text.toString()
        WolvesAPI.loginAP(account, password) { token ->
            toLog("[MainActivity] login:$token")
            this.loginToken = token
        }
    }

    fun onPlayerInfo(view: View) {
        WolvesAPI.playInfo(this.loginToken) { token ->
            toLog("[MainActivity] onPlayerInfo:$token")
        }
    }

    fun onWebFBID(view: View) {
        println ("[MainActivity] onWebFBID")
        WolvesAPI.getWebFBID(this, this.findViewById(R.id.main_root)){ key ->
            toLog("[MainActivity] onWebFBID:$key")
        }
    }

}

