// DESC : 登入相關
// Author : nick.shih

package com.wolves.wolveslib

import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


object LoginMgr {

    // 跳出登入介面
    fun onLogin(context: AppCompatActivity) {
        println("[LoginMgr] onLogin")
        //-----------------------------------------------
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
        dialog.window?.setLayout(400, 300)

        // 設定按鈕動作
        val btn_login = dialog.findViewById<Button>(com.wolves.wolveslib.R.id.btn_login_account)
        btn_login.setOnClickListener {
            println ("[LoginMgr] btn_login")
            dialog.dismiss()
        }
        val btn_fb = dialog.findViewById<Button>(com.wolves.wolveslib.R.id.btn_login_fb)
        btn_fb.setOnClickListener {
            println ("[LoginMgr] btn_fb")
            dialog.dismiss()
        }

        //-----------------------------------------------
        // 第三種方法
    }
}
