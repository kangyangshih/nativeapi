// DESC : 和 API 串接的功能
package com.wolves.wolveslib

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object WebAPI {
    // 產生遊客帳號
    fun createGuest (callback: (String) -> Unit) {
        val okHttpClient = OkHttpClient()
        val body = FormBody.Builder()
            .add("account", "1")
            .add("password", "2")
            .build()
        val request = Request.Builder()
            .url("https://user-anun.yongxu.com.tw/anun/v1/guest")
            .post(body)
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("fail : $e")
            }
            // {"data":{"account":"g658118095","password":"mwmCePfZklIf9spck4eY"},"code":1}
            override fun onResponse(call: Call?, response: Response?) {
                //處理回來的 Response
                val responseStr = response!!.body()!!.string()
                println (responseStr)
                callback (responseStr)
                //val itemList = JSONObject(responseStr)
            }
        })
    }
}