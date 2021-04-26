// DESC : 用來串接 FB 的登入動作
// DATE : 2021/4/21

package com.wolves.anunapidemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.BuildConfig
import com.facebook.R
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

object FBSystem {

    lateinit var mContext: AppCompatActivity
    lateinit var callbackManager: CallbackManager

    fun initObject (context : AppCompatActivity) {
        println("~【initObject】BillingSystem ~")
        // 使用的實體
        this.mContext = context

        FacebookSdk.sdkInitialize(this.mContext.getApplicationContext());
        AppEventsLogger.activateApp(this.mContext);
        callbackManager = CallbackManager.Factory.create();
        val loginButton = this.mContext.findViewById<LoginButton>(com.wolves.anunapidemo.R.id.login_button)
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
            }

            override fun onError(exception: FacebookException) {
                Log.d("TAG", "onError")
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
                    AndroidAPI.mLoginFBFinish (AccessToken.getCurrentAccessToken().getToken())
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
                val loginButton = this.mContext.findViewById<LoginButton>(com.wolves.anunapidemo.R.id.login_button)
                loginButton.performClick()
                println ("[LogoutFB]")
            }).executeAsync()
    }

    // 做登入FB的動作
    fun loginFB () {
        this.mContext.runOnUiThread(java.lang.Runnable
        {
            // 做登入的動作
            val loginButton = this.mContext.findViewById<LoginButton>(com.wolves.anunapidemo.R.id.login_button)
            loginButton.performClick()
        })

    }

}