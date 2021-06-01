// DESC : 這邊是存放和 Android 相關的 API 功能
// Author : nick.shih
package com.wolves.wolveslib

import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object AndroidAPI {
    // 顯示字串
    fun showMsg(msg: String) {
        println ("[showMsg] $msg")
        //Toast.makeText(ShareData.mContext, msg, Toast.LENGTH_LONG).show();
    }

    // for fackbook 串接做使用
    fun showAPKHashCode(context: AppCompatActivity, name : String="com.wolves.demoapi") {
        try {
            val info = context.packageManager.getPackageInfo(
                name,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

}
