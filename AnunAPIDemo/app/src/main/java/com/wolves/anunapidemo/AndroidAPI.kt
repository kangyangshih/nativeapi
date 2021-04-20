// DESC : 提供串接 API 的 API

package com.wolves.anunapidemo

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

object AndroidAPI {
    // 使用的活動實體
    lateinit var mContext: AppCompatActivity
    // 做初使化的動作
    fun initObject (context : AppCompatActivity){
        println ("~【initObject】~")
        // 使用的實體
        this.mContext = context
    }

    //--------------------------------------------------
    // 提供呼叫的 API
    //--------------------------------------------------
    // 打開登入 / 註冊
    fun startLogin (){
        // 打開介面
        val LoginUIPopupWindow: cLoginUIPopupWindow by lazy {
            cLoginUIPopupWindow.ConfirmPopupWindowBuilder.init(this.mContext).build()
        }
        LoginUIPopupWindow.show()
    }

    // 設定儲值品項的字串
    fun setDepositItemStr (){
    }

    // 做儲值的動作
    fun startDeposit (itemStr : String){
    }

}
