// DESC : 和 API 串接的功能
package com.wolves.wolveslib

import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object WebAPI {
    //---------------------------------------------------
    // 產生遊客帳號
    fun createGuest (callback: (JSONObject) -> Unit) {
        val okHttpClient = OkHttpClient()
        val body = FormBody.Builder()
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
            override fun onResponse(call: Call?, response: Response?) {
                //處理回來的 Response
                val responseStr = response!!.body()!!.string()
                val res = JSONObject()
                println (responseStr)
                val itemMap = JSONObject(responseStr)
                //println (itemMap.getJSONObject("data").get ("account"))
                res.put ("account", itemMap.getJSONObject("data").getString ("account"))
                //println (itemMap.getJSONObject("data").get ("password"))
                res.put ("password", itemMap.getJSONObject("data").getString ("password"))
                callback (res)
            }
        })
    }

    //---------------------------------------------------
    // 遊戲登入
    // {"data":{"account":"g658118095","password":"mwmCePfZklIf9spck4eY"},"code":1}
    fun login (account:String, password:String, callback: (String) -> Unit) {
        val okHttpClient = OkHttpClient()
        val body = FormBody.Builder()
            .add("account", account)
            .add("password", password)
            .build()
        val request = Request.Builder()
            .url("https://user-anun.yongxu.com.tw/anun/v1/login")
            .post(body)
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("fail : $e")
            }
            override fun onResponse(call: Call?, response: Response?) {
                //處理回來的 Response
                val responseStr = response!!.body()!!.string()
                println (responseStr)
                val itemMap = JSONObject(responseStr)
                callback (itemMap.getJSONObject("data").getString ("token"))
            }
        })
    }

    //---------------------------------------------------
    // 用戶資訊
    // {"data":{"token":"bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdXNlci1hbnVuLnlvbmd4dS5jb20udHdcL2FudW5cL3YxXC9sb2dpbiIsImlhdCI6MTYyMjYwMjM3MiwiZXhwIjoxNjIyNjIzOTcyLCJuYmYiOjE2MjI2MDIzNzIsImp0aSI6IkxROXVvczBrVjhoZDFsaFIiLCJzdWIiOjcwMjU4NzA4NDk1NjAxNjY0LCJwcnYiOiI4NjY1YWU5Nzc1Y2YyNmY2YjhlNDk2Zjg2ZmE1MzZkNjhkZDcxODE4In0.4_O98QQ5LZ8JcWKitnJ0P4f9VUVJ2RC4azRTyz4rQ7c"},"code":1}
    // token : bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvdXNlci1hbnVuLnlvbmd4dS5jb20udHdcL2FudW5cL3YxXC9sb2dpbiIsImlhdCI6MTYyMjU1MzkzMywiZXhwIjoxNjIyNTc1NTMzLCJuYmYiOjE2MjI1NTM5MzMsImp0aSI6Ilo3b21LYVVtY3VsMGQyazAiLCJzdWIiOjcwMjU4NzA4NDk1NjAxNjY0LCJwcnYiOiI4NjY1YWU5Nzc1Y2YyNmY2YjhlNDk2Zjg2ZmE1MzZkNjhkZDcxODE4In0.G7zyOcDyUZgdrSb3SbAXPTQcogh1vIridgtlBsjTdds
    fun userInfo (token:String, callback: (JSONObject) -> Unit) {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .addHeader("authorization", token)
            .url("https://user-anun.yongxu.com.tw/anun/v1/user")
            .get()
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("fail : $e")
            }
            // {"data":{"account":"g658118095","nickname":"\u8a66\u73a9\u73a9\u5bb6","credit":100,"phone_number":null,"facebook_nickname":null},"code":1}
            override fun onResponse(call: Call?, response: Response?) {
                //處理回來的 Response
                val responseStr = response!!.body()!!.string()
                println (responseStr)
                val itemMap = JSONObject(responseStr)
                callback (itemMap.getJSONObject("data"))
            }
        })
    }

    fun getFBID () {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url("https://front.fishrush.com.tw/website/profile")
            .get()
            .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("fail : $e")
            }
            override fun onResponse(call: Call?, response: Response?) {
                //處理回來的 Response
                val responseStr = response!!.body()!!.string()
                println (responseStr)
            }
        })
    }
}