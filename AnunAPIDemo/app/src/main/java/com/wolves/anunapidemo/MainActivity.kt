package com.wolves.anunapidemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.BuildConfig
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class MainActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        println("[system] onStart------------------")
    }

    override fun onResume() {
        super.onResume()
        println("[system] onResume------------------")
    }

    override fun onPause() {
        super.onPause()
        println("[system] onPause------------------")
    }

    override fun onStop() {
        super.onStop()
        println("[system] onStop------------------")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("[system] onDestroy------------------")
    }

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        println("[system] onCreate------------------")
        super.onCreate(savedInstanceState)
        // [API] 做初使化的動作
        setContentView(R.layout.activity_main)

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.d("TAG", "Success Login")
                Log.d("TAG", loginResult?.accessToken?.userId.toString())
                // Get User's Info
                getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)
            }

            override fun onCancel() {
                Log.d("TAG", "onCancel")
                Toast.makeText(this@MainActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                Log.d("TAG", "onError")
                Toast.makeText(this@MainActivity, exception.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {
        println ("【getUserProfile】")
        val parameters = Bundle()
        parameters.putString(
                "fields",
                "id, name"
        )
        Log.d("TAG", token.toString())
        GraphRequest(token,
                "/$userId/",
                parameters,
                HttpMethod.GET,
                GraphRequest.Callback { response ->
                    val jsonObject = response.jsonObject

                    // Facebook Access Token
                    // You can see Access Token only in Debug mode.
                    // You can't see it in Logcat using Log.d, Facebook did that to avoid leaking user's access token.
                    if (BuildConfig.DEBUG) {
                        FacebookSdk.setIsDebugEnabled(true)
                        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                    }

                    // Facebook Id
                    if (jsonObject.has("id")) {
                        val facebookId = jsonObject.getString("id")
                        Log.i("Facebook Id: ", facebookId.toString())
                        //ShareData.mFBID = facebookId.toString()
                        Log.i("Access Id: ", AccessToken.getCurrentAccessToken().getToken())
                    } else {
                        Log.i("Facebook Id: ", "Not exists")
                    }

                    // Facebook Profile Pic URL
                    if (jsonObject.has("picture")) {
                        val facebookPictureObject = jsonObject.getJSONObject("picture")
                        if (facebookPictureObject.has("data")) {
                            val facebookDataObject = facebookPictureObject.getJSONObject("data")
                            if (facebookDataObject.has("url")) {
                                val facebookProfilePicURL = facebookDataObject.getString("url")
                                Log.i("Facebook Profile Pic URL: ", facebookProfilePicURL)
                            }
                        }
                    } else {
                        Log.i("Facebook Profile Pic URL: ", "Not exists")
                    }
                    // 做登出的動作
                    val loginButton = findViewById<LoginButton>(R.id.login_button)
                    loginButton.performClick()
                    println ("[LogoutFB]")
                }).executeAsync()
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
