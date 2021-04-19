// DESC : 提供串接 API 的 API

package com.wolves.anunapidemo

import androidx.appcompat.app.AppCompatActivity

object AndroidAPI {
    // 使用的活動實體
    lateinit var mContext: AppCompatActivity
    // 做初使化的動作
    fun initObject (context : AppCompatActivity){
        // 使用的實體
        this.mContext = context
    }

    //--------------------------------------------------
    // 提供呼叫的 API
    //--------------------------------------------------
    // 打開登入 / 註冊
    fun startLogin (){
        // 打開介面
    }

    // 設定儲值品項的字串
    fun setDepositItemStr (){
    }

    // 做儲值的動作
    fun startDeposit (itemStr : String){
    }

}
