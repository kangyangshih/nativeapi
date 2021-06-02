// DESC : 登入相關
// Author : nick.shih

package com.wolves.wolveslib

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object LoginMgr {

    // 跳出登入介面
    fun onLogin(context: AppCompatActivity) {
        println("[LoginMgr] onLogin")
        //--------------------------------------------ㄌ---
        // 第一種動態跳出的方式
        // 取得主介面的 float
        //val flater = LayoutInflater.from(context)
        // 把介面動態塞進去
        //val view: View = flater.inflate(com.wolves.wolveslib.R.layout.wolves_login, null)

        //-----------------------------------------------
        // 第二種動態跳出的方式 -> 使用 dialog 的方式
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = context.getLayoutInflater()
        val layout: View = inflater.inflate(com.wolves.wolveslib.R.layout.wolves_login, null)
        builder.setView(layout)
        val dialog = builder.create()

        // 做顯示的動作
        dialog.show()

        // 設定大小
        dialog.window?.setLayout(400, 320)

        // Guest 登入
        val btn_login = dialog.findViewById<Button>(com.wolves.wolveslib.R.id.btn_login_account)
        btn_login.setOnClickListener {
            println("[LoginMgr] btn_login")
            // 產生帳號
            WebAPI.createGuest { jsonObject ->
                //println("[LoginMgr] callback createGuest:$jsonStr")
                context.runOnUiThread {
                    var account : String = jsonObject.getString ("account")
                    var password : String = jsonObject.getString("password")
                    Toast.makeText(context, "[LoginMgr][createGuest] $account, $password", Toast.LENGTH_LONG).show();
                }
            }

            // 登入
            //WebAPI.login("g658118095", "mwmCePfZklIf9spck4eY") { jsonStr ->
            //    context.runOnUiThread {
            //        Toast.makeText(
            //            context,
            //            "[LoginMgr] callback createGuest:$jsonStr",
            //            Toast.LENGTH_LONG
            //        ).show();
            //    }
            //}

            // 取得用戶資訊
            //WebAPI.userInfo("bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdXNlci1hbnVuLnlvbmd4dS5jb20udHdcL2FudW5cL3YxXC9sb2dpbiIsImlhdCI6MTYyMjU1MzkzMywiZXhwIjoxNjIyNTc1NTMzLCJuYmYiOjE2MjI1NTM5MzMsImp0aSI6Ilo3b21LYVVtY3VsMGQyazAiLCJzdWIiOjcwMjU4NzA4NDk1NjAxNjY0LCJwcnYiOiI4NjY1YWU5Nzc1Y2YyNmY2YjhlNDk2Zjg2ZmE1MzZkNjhkZDcxODE4In0.G7zyOcDyUZgdrSb3SbAXPTQcogh1vIridgtlBsjTdds"){ jsonStr ->
            //    context.runOnUiThread {
            //        Toast.makeText(
            //            context,
            //            "[LoginMgr] callback createGuest:$jsonStr",
            //            Toast.LENGTH_LONG
            //        ).show();
            //    }
            //}
            // 關閉視窗
            dialog.dismiss()
        }

        //-----------------------------------------------
        // FB 登入
        val btn_fb = dialog.findViewById<Button>(com.wolves.wolveslib.R.id.btn_login_fb)
        btn_fb.setOnClickListener {
            println("[LoginMgr] btn_fb")
            LoginFB(context)
            //dialog.dismiss()
        }

        //-----------------------------------------------
        // 初使化FB
        FBMgr.initObject(context, dialog)
    }

    //--------------------------------------------------
    // FB 登入
    //--------------------------------------------------
    fun LoginFB(context: AppCompatActivity) {
        println("~【AndroidAPI】LoginFB")
        FBMgr.loginFB()
    }

    fun onFBCallback(openid: String) {
        println("~【AndroidAPI】openID : [$openid]")
    }
}
